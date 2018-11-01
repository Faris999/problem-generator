package com.faris.questiongenerator;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private Scanner sc;

    private Main() {
        sc = new Scanner(System.in);
        int correct = 0;
        float score;
        Class type = getType();
        Object question = null;
        try {
            question = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> questions = new HashMap<>();
        if (question instanceof PLSV) {
            questions = ((PLSV) question).generate(20);
        } else if (question instanceof Integers) {
            questions = ((Integers) question).generate(20);
        } else if (question instanceof Gradient) {
            questions = ((Gradient) question).generate(20);
        }
        Set<String> keySet = questions.keySet();
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(keySet.toArray()[i]);
            int answer = sc.nextInt();
            if (answer == questions.get(keySet.toArray(new String[0])[i])) {
                System.out.println("Correct!");
                correct++;
            } else {
                System.out.println("Correct Answer: " + questions.get(keySet.toArray(new String[0])[i]));
            }
            System.out.println("Questions Answered: " + (i + 1) + " of 20");
            System.out.println();
        }
        score = (float) (correct / 20.0) * 100;
        System.out.println("Your score is " + score);
    }

    public static void main(String[] args) {
        new Main();
    }

    private Class getType() {
        System.out.println("What subject you want to choose?");
        String type = sc.nextLine();
        Class clazz;
        try {
            clazz = Class.forName("com.faris.questiongenerator." + type);
        } catch (ClassNotFoundException e) {
            System.err.println("There are no subject named " + type);
            return getType();
        }
        return clazz;
    }


}
