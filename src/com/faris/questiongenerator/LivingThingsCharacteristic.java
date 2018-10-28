package com.faris.questiongenerator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LivingThingsCharacteristic {

    private ArrayList<LivingThing> livingThings = new ArrayList<>();

    HashMap<String, String> generate() {
        prepareData();
        Random r = new Random();
        int num = r.nextInt(livingThings.size());
        LivingThing livingThing = livingThings.get(num);
        num = r.nextInt(livingThing.getCharacteristics().size());
        String question = "What is the characteristics that helps " + livingThing.getName() + " " + livingThing.getCharacteristics().get(livingThing.getCharacteristics().keySet().toArray(new String[0])[num]) + "?";
        String answer = livingThing.getCharacteristics().keySet().toArray(new String[0])[num];
        HashMap<String, String> map = new HashMap<>();
        map.put(question, answer);
        return map;
    }

    private void prepareData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LivingThing>>() {
        }.getType();
        try {
            livingThings = gson.fromJson(new FileReader(new File("res/characteristics.json")), type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
