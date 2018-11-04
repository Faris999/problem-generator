package com.faris.questiongenerator.main;

import com.faris.questiongenerator.database.DBHelper;
import com.faris.questiongenerator.generator.Problem;
import com.faris.questiongenerator.generator.ProblemInteger;
import com.faris.questiongenerator.generator.ProblemString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private Scanner sc;

    private Main() {
        sc = new Scanner(System.in);
        setupDB();
        Class type = getType();
        Object question = new Object();
        try {
            question = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (question instanceof ProblemInteger) {
            ask((ProblemInteger) question);
        } else if (question instanceof ProblemString) {
            ask((ProblemString) question);
        }

    }

    private <T> void ask(Problem<T> question) {
        int numCorrect = 0;
        float score;
        String[] correct = new String[5];
        HashMap<String, T> questions = question.generate(5);
        Set<String> keySet = questions.keySet();
        if (question instanceof ProblemInteger) {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println(keySet.toArray()[i]);
                int answer = sc.nextInt();
                correct[i] = String.valueOf(answer);
                if (answer == (Integer) questions.get(keySet.toArray(new String[0])[i])) {
                    System.out.println("Correct!");
                    numCorrect++;
                } else {
                    System.out.println("Correct Answer: " + questions.get(keySet.toArray(new String[0])[i]));
                }
                System.out.println("Questions Answered: " + (i + 1) + " of 5");
                System.out.println();
            }
        } else if (question instanceof ProblemString) {
            for (int i = 0; i < questions.size(); i++) {
                System.out.println(keySet.toArray()[i]);
                String answer = sc.nextLine();
                correct[i] = answer;
                if (answer.equals(questions.get(keySet.toArray(new String[0])[i]))) {
                    System.out.println("Correct!");
                    numCorrect++;
                } else {
                    System.out.println("Correct Answer: " + questions.get(keySet.toArray(new String[0])[i]));
                }
                System.out.println("Questions Answered: " + (i + 1) + " of 5");
                System.out.println();
            }
        }
        score = (float) (numCorrect / 5.0) * 100;
        System.out.println("Your score is " + score);
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        DBHelper.insert("main", score, question.getClass().getSimpleName(), new Timestamp(System.currentTimeMillis()), gson.toJson(questions, type), gson.toJson(correct));
    }

    public static void main(String[] args) {
        new Main();
    }

    private Class getType() {
        System.out.println("What subject you want to choose?");
        String type = sc.nextLine();
        Class clazz;
        try {
            clazz = Class.forName("com.faris.questiongenerator.generator." + type);
        } catch (ClassNotFoundException e) {
            System.err.println("There are no subject named " + type);
            return getType();
        }
        return clazz;
    }

    private void setupDB() {
        DBHelper.createNewTable("main");
    }

}
