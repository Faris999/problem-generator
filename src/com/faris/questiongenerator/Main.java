package com.faris.questiongenerator;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Gradient gradient = new Gradient();
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> questions = gradient.generate(100);
        Set<String> keySet = questions.keySet();
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(keySet.toArray()[i]);
            int answer = sc.nextInt();
            if (answer == questions.get(keySet.toArray(new String[0])[i])) {
                System.out.println("Correct!");
            } else {
                System.out.println("Correct Answer: " + questions.get(keySet.toArray(new String[0])[i]));

            }
            System.out.println("Questions Answered: " + (i + 1));
            System.out.println();
        }

    }
}
