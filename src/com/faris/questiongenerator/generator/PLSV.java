package com.faris.questiongenerator.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PLSV extends ProblemInteger {

    private Random r;
    private float a, b, c, answer;
    private ArrayList<ArrayList<Float>> log = new ArrayList<>();
    private Type type;

    public PLSV() {
    }

    public HashMap<String, Integer> generate(int num) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            loop:
            while (true) {
                generateNumber();
                for (ArrayList<Float> list : log) {
                    if (a == list.get(0) && b == list.get(1) && c == list.get(2)) continue loop;
                }
                if (checkEasy(type) && answer != 0) break;
            }
            log.add(new ArrayList<>());
            log.get(log.size() - 1).add(a);
            log.get(log.size() - 1).add(b);
            log.get(log.size() - 1).add(c);
            String question;
            if (type == Type.A) {
                if (a == 1)
                    question = "x + b = c".replace("b", String.valueOf((int) b)).replace("c", String.valueOf((int) c));
                else if (b == 0)
                    question = "ax = c".replace("a", String.valueOf((int) a)).replace("c", String.valueOf((int) c));
                else
                    question = "ax + b = c".replace("a", String.valueOf((int) a)).replace("b", String.valueOf((int) b)).replace("c", String.valueOf((int) c));
                map.put("What is the value of x?\n" +
                        question, (int) answer);
            } else if (type == Type.B) {
                if (a == 1)
                    question = "x + b + c = 0".replace("b", String.valueOf((int) b)).replace("c", String.valueOf((int) c));
                else if (b == 0)
                    question = "ax + c = 0".replace("a", String.valueOf((int) a)).replace("c", String.valueOf((int) c));
                else
                    question = "ax + b = c".replace("a", String.valueOf((int) a)).replace("b", String.valueOf((int) b)).replace("c", String.valueOf((int) c));
                map.put("What is the value of x?\n" +
                        question, (int) answer);
            }
        }
        return map;
    }

    private void generateNumber() {
        r = new Random();
        a = r.nextInt(40) - 20;
        b = r.nextInt(40) - 20;
        c = r.nextInt(40) - 20;
        type = Type.values()[r.nextInt(Type.values().length - 1)];
        switch (type) {
            case A:
                answer = (c - b) / a;
                break;
            case B:
                answer = (-c - b) / a;
                break;
        }
    }

    private boolean checkEasy(Type type) {
        switch (type) {
            case A:
                return ((c - b) % a) == 0;
            case B:
                return ((-c - b) % a) == 0;
        }
        return true;
    }

    private enum Type {
        A, B, C
    }

}
