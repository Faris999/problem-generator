package com.faris.questiongenerator.generator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LivingThingsCharacteristic extends ProblemString {

    private ArrayList<LivingThing> livingThings = new ArrayList<>();
    private Type type;
    private String answer, feature, characteristicName;
    private LivingThing livingThing;
    private Characteristic characteristic;
    private ArrayList<ArrayList<Object>> log = new ArrayList<>();

    public HashMap<String, String> generate(int num) {
        prepareData();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            loop:
            while (true) {
                generateQuestion();
                for (ArrayList<Object> list : log) {
                    if (livingThing.getName() == list.get(0) && characteristicName == list.get(1) && type == list.get(2)) {
                        continue loop;
                    }
                }
                break;
            }
            log.add(new ArrayList<>());
            log.get(log.size() - 1).add(livingThing.getName());
            log.get(log.size() - 1).add(characteristicName);
            log.get(log.size() - 1).add(type);
            String question = "";
            if (type == Type.A) {
                question = "What is the characteristics that helps " + livingThing.getName() + " " + feature + "?";
            } else if (type == Type.B) {
                question = "What animal that have ";
                if (characteristic.isNoun()) {
                    if (characteristic.startsWithVocal()) {
                        question += "an ";
                    } else {
                        question += "a ";
                    }
                }
                question += characteristicName + "?";
            }
            map.put(question, answer);
        }

        return map;
    }

    private void generateQuestion() {
        Random r = new Random();
        type = Type.values()[r.nextInt(Type.values().length)];
        livingThing = livingThings.get(r.nextInt(livingThings.size()));
        int nextInt = r.nextInt(livingThing.getCharacteristics().size());
        characteristic = livingThing.getCharacteristics().get(nextInt);
        characteristicName = characteristic.getName();
        feature = characteristic.getFunction();
        switch (type) {
            case A:
                answer = characteristicName;
                break;
            case B:
                answer = livingThing.getName();
                break;
        }
    }

    private void prepareData() {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<LivingThing>>() {
        }.getType();
        try {
            livingThings = gson.fromJson(new FileReader(new File("res/characteristics.json")), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private enum Type {
        A, B
    }
}
