package com.faris.questiongenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Gradient {

    private int a, b, c, d, answer;
    private ArrayList<ArrayList<Integer>> log = new ArrayList<>();
    private Type type;

    public Gradient() {

    }

    HashMap<String, Integer> generate(int num) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            loop:
            while (true) {
                generateNumber();
                for (ArrayList<Integer> list : log) {
                    if (a == list.get(0) && b == list.get(1) && c == list.get(2) && d == list.get(3) && type.ordinal() == list.get(4))
                        continue loop;
                }
                if (checkEasy(type) && answer != 0) break;
            }
            log.add(new ArrayList<>());
            log.get(log.size() - 1).add(a);
            log.get(log.size() - 1).add(b);
            log.get(log.size() - 1).add(c);
            log.get(log.size() - 1).add(d);
            log.get(log.size() - 1).add(type.ordinal());
            String question;
            if (type == Type.SlopeIntercept) {
                question = "y = mx + b".replace("m", String.valueOf(a)).replace("b", String.valueOf(b));
                map.put("What is the gradient of:\n" + question + " ?", answer);
            } else if (type == Type.Formal) {
                question = "ay + bx + c = 0".replace("a", String.valueOf(a)).replace("b", String.valueOf(b)).replace("c", String.valueOf(c));
                map.put("What is the gradient of:\n" + question + " ?", answer);
            } else if (type == Type.XY) {
                question = "ay + bx = c".replace("a", String.valueOf(a)).replace("b", String.valueOf(b)).replace("c", String.valueOf(c));
                map.put("What is the gradient of:\n" + question + " ?", answer);
            } else if (type == Type.Points) {
                question = "(a,b) & (c,d)".replace("a", String.valueOf(a)).replace("b", String.valueOf(b)).replace("c", String.valueOf(c)).replace("d", String.valueOf(d));
                map.put("What is the gradient of a line that passes the point " + question + " ?", answer);
            }
        }
        return map;
    }

    private boolean checkEasy(Type type) {
        switch (type) {
            case Points:
                return (b - d) % (a - c) == 0;
            case Formal:
            case XY:
                return -b % a == 0;
            default:
                return true;
        }
    }

    private void generateNumber() {
        Random r = new Random();
        a = r.nextInt(20) - 10;
        b = r.nextInt(20) - 10;
        c = r.nextInt(20) - 10;
        d = r.nextInt(20) - 10;
        type = Type.values()[r.nextInt(Type.values().length)];
        switch (type) {
            case SlopeIntercept:
                answer = a;
                break;
            case Formal:
            case XY:
                while (a == 0) {
                    a = r.nextInt(20) - 10;
                }
                answer = -b / a;
                break;
            case Points:
                while ((a - c) == 0) {
                    a = r.nextInt(20) - 10;
                    c = r.nextInt(20) - 10;
                }
                answer = (b - d) / (a - c);
                break;
        }
    }

    private enum Type {
        SlopeIntercept,
        Formal,
        XY,
        Points
    }

}
