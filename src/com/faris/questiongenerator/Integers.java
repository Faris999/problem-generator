package com.faris.questiongenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Integers {

    private int a, b, c, answer;
    private ArrayList<ArrayList<Integer>> log = new ArrayList<>();
    private Type type;

    public Integers() {

    }

    HashMap<String, Integer> generate(int num) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            loop:
            while (true) {
                generateNumber();
                for (ArrayList<Integer> list : log) {
                    if (a == list.get(0) && b == list.get(1) && c == list.get(2) && type.ordinal() == list.get(3))
                        continue loop;
                }
                if (checkEasy(type) && answer != 0) break;
            }
            log.add(new ArrayList<>());
            log.get(log.size() - 1).add(a);
            log.get(log.size() - 1).add(b);
            log.get(log.size() - 1).add(c);
            log.get(log.size() - 1).add(type.ordinal());
            String question;
            if (type == Type.Addition) {
                question = "a + b = ...".replace("a", String.valueOf(a)).replace("b", String.valueOf(b));
                map.put(question, answer);
            } else if (type == Type.Subtraction) {
                question = "a - b = ...".replace("a", String.valueOf(a)).replace("b", String.valueOf(b));
                map.put(question, answer);
            } else if (type == Type.Multiplication) {
                question = "a * b = ...".replace("a", String.valueOf(a)).replace("b", String.valueOf(b));
                map.put(question, answer);
            } else if (type == Type.Division) {
                question = "a / b = ...".replace("a", String.valueOf(a)).replace("b", String.valueOf(b));
                map.put(question, answer);
            } else if (type == Type.AdditionMultiplication) {
                question = "a + b * c = ...".replace("a", String.valueOf(a)).replace("b", String.valueOf(b)).replace("c", String.valueOf(c));
                map.put(question, answer);
            }
        }
        return map;
    }

    private boolean checkEasy(Type type) {
        switch (type) {
            case Division:
                return a % b == 0;
            default:
                return true;
        }
    }

    private void generateNumber() {
        Random r = new Random();
        a = r.nextInt(20) - 10;
        b = r.nextInt(20) - 10;
        c = r.nextInt(20) - 10;
        type = Type.values()[r.nextInt(Type.values().length)];
        //type = Type.AdditionMultiplication;
        switch (type) {
            case Addition:
                answer = a + b;
                break;
            case Subtraction:
                answer = a - b;
                break;
            case Multiplication:
                answer = a * b;
                break;
            case Division:
                while (b == 0) b = r.nextInt(40) - 20;
                answer = a / b;
                break;
            case AdditionMultiplication:
                answer = a + b * c;
        }
    }

    private enum Type {
        Addition,
        Subtraction,
        Multiplication,
        Division,
        AdditionMultiplication
    }

}
