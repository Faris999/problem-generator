package com.faris.questiongenerator.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PGL extends ProblemString {

    private Random r;
    private int a, b, c, d;
    private String answer;
    private ArrayList<ArrayList<Integer>> log = new ArrayList<>();
    private Type type;

    public PGL() {
    }

    public HashMap<String, String> generate(int num) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < num; i++) {
            loop:
            while (true) {
                generateNumber();
                for (ArrayList<Integer> list : log) {
                    if (a == list.get(0) && b == list.get(1) && c == list.get(2) && type.ordinal() == list.get(4)) {
                        if (type == Type.A) {
                            if (d == list.get(3)) {
                                continue loop;
                            }
                        } else continue loop;
                    }
                }
                if (checkEasy(type)) break;
            }
            log.add(new ArrayList<>());
            log.get(log.size() - 1).add(a);
            log.get(log.size() - 1).add(b);
            log.get(log.size() - 1).add(c);
            log.get(log.size() - 1).add(d);
            log.get(log.size() - 1).add(type.ordinal());
            String question;
            if (type == Type.A) {
                question = String.format("What is the equation of a line which passes the point (%d,%d) and (%d,%d)?", a, b, c, d);
                map.put(question, answer);
            } else if (type == Type.B) {
                question = String.format("What is the equation of a line which passes the point (%d,%d) and have a gradient of %d", a, b, c);
                map.put(question, answer);
            }
        }
        return map;
    }

    private void generateNumber() {
        r = new Random();
        a = r.nextInt(20) - 10;
        b = r.nextInt(20) - 10;
        c = r.nextInt(20) - 10;
        d = r.nextInt(20) - 10;
        int m = 0;
        int intercept = 0;
        type = Type.values()[r.nextInt(Type.values().length)];
        switch (type) {
            case A:
                do {
                    a = r.nextInt(20) - 10;
                    c = r.nextInt(20) - 10;
                } while ((a - c) == 0);
                m = (b - d) / (a - c);
                intercept = b - m * a;
                break;
            case B:
                m = c;
                intercept = b - m * a;
                break;
        }
        answer = "y = %dx + %d";
        answer = String.format(answer, m, intercept);
        if (intercept == 0) {
            answer = "y = %dx";
            answer = String.format(answer, m, intercept);
            if (m == 0) {
                answer = "y = 0";
            } else if (m == -1) {
                answer = "y = -x";
            } else if (m == 1) {
                answer = "y = x";
            }
        } else if (intercept < 0) {
            intercept = Math.abs(intercept);
            answer = "y = %dx - %d";
            answer = String.format(answer, m, intercept);
            if (m == 0) {
                answer = "y = %d";
                answer = String.format(answer, intercept);
            } else if (m == -1) {
                answer = "y = -x %d";
                answer = String.format(answer, intercept);
            } else if (m == 1) {
                answer = "y = x %d";
                answer = String.format(answer, intercept);
            }
        } else if (m == 0) {
            answer = "y = %d";
            answer = String.format(answer, intercept);
        } else if (m == -1) {
            answer = "y = -x + %d";
            answer = String.format(answer, intercept);
        } else if (m == 1) {
            answer = "y = x + %d";
            answer = String.format(answer, intercept);
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(new File("res/log.txt")));
            printWriter.println(a);
            printWriter.println(b);
            printWriter.println(c);
            printWriter.println(d);
            printWriter.println(m);
            printWriter.println(intercept);
            printWriter.println(answer);
            printWriter.println();
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkEasy(Type type) {
        switch (type) {
            case A:
                return ((b - d) % (a - c)) == 0;
        }
        return true;
    }

    private enum Type {
        A, B
    }

}
