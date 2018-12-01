package com.faris.questiongenerator.main;

import com.faris.questiongenerator.database.DBHelper;
import com.faris.questiongenerator.generator.Problem;
import com.faris.questiongenerator.generator.ProblemInteger;
import com.faris.questiongenerator.generator.ProblemString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private Scanner sc;
    private ArrayList<String> subjects = new ArrayList<>();

    Main() {
        System.out.println("Question Generator v0.1");
        sc = new Scanner(System.in);
        subjects.add("Integers");
        subjects.add("PLSV");
        subjects.add("Gradient");
        subjects.add("PGL");
        setupDB();
        getCommand();
    }

    private void getCommand() {
        System.out.print(">");
        String[] commands = sc.nextLine().split(" ");
        switch (commands[0]) {
            case "answer":
                if (commands.length < 2)
                    prepareAsk(5);
                else
                    prepareAsk(Integer.valueOf(commands[1]));
                break;
            case "insights":
                getInsights();
                break;
            case "list":
                for (String subject : subjects) {
                    System.out.println(subject);
                }
                break;
            case "help":
                System.out.println("answer [num] Generate questions to answer.");
                System.out.println("insights View insights.");
                System.out.println("list See list of subjects.");
            default:
                System.out.println("No command named '" + commands[0] + "'");
                getCommand();
        }
    }

    private void getInsights() {
        Gson gson = new Gson();
        ArrayList<String> stringSubjects = new ArrayList<>();
        stringSubjects.add("PGL");
        for (String subject : subjects) {
            System.out.println();
            System.out.println(subject + ":");
            System.out.println();
            ResultSet rs = DBHelper.select("main", "SELECT * FROM score WHERE subject = \"" + subject + "\"");

            int i = 0;
            ArrayList<HashMap<String, String>> questions = new ArrayList<>();
            ArrayList<String[]> answers = new ArrayList<>();
            ArrayList<Float> scores = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            try {
                for (; rs.next(); i++) {
                    Type type;
                    HashMap<String, String> stringQuestions = new HashMap<>();
                    if (stringSubjects.contains(subject)) {
                        type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        stringQuestions = gson.fromJson(rs.getString("questions"), type);
                    } else {
                        type = new TypeToken<HashMap<String, Integer>>() {
                        }.getType();
                        HashMap<String, Integer> integerQuestions = gson.fromJson(rs.getString("questions"), type);
                        for (String question : integerQuestions.keySet()) {
                            stringQuestions.put(question, integerQuestions.get(question).toString());
                        }
                    }
                    String[] answer = gson.fromJson(rs.getString("correct"), String[].class);
                    float score = rs.getFloat("score");
                    String date = rs.getString("date");
                    questions.add(stringQuestions);
                    answers.add(answer);
                    scores.add(score);
                    dates.add(date);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (i == 0) {
                System.out.println("No data for this subject.");
                continue;
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileWriter(new File("res/" + subject + "-insights.csv")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Scores over time:");
            for (int l = 0; l < dates.size(); l++) {
                String date = dates.get(l);
                float score = scores.get(l);
                System.out.println(date);
                if (writer != null) {
                    writer.print(date + ",");
                    writer.println(score);
                }
                System.out.println(score);
                System.out.println();
            }
            if (writer != null) {
                writer.flush();
                writer.close();
            }

            int wrong = 0;
            int total = 0;
            for (int k = 0; k < questions.size(); k++) {
                HashMap<String, String> stringQuestions = questions.get(k);
                String[] answer = answers.get(k);

                int j = 0;
                for (String question : stringQuestions.keySet()) {
                    if (!stringQuestions.get(question).equals(answer[j])) {
                        System.out.println(question);
                        System.out.println("Your answer: " + answer[j]);
                        System.out.println("Correct answer: " + stringQuestions.get(question));
                        System.out.println();
                        wrong++;
                    }
                    j++;
                    total++;
                }
            }
            System.out.printf("Average score 2: %.2f\n", (total - wrong) * 100 / (float) total);
        }

    }

    private void prepareAsk(int num) {
        Class type = getType();
        Object question = new Object();
        try {
            question = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (question instanceof ProblemInteger) {
            ask((ProblemInteger) question, num);
        } else if (question instanceof ProblemString) {
            ask((ProblemString) question, num);
        }
    }

    private <T> void ask(Problem<T> question, int num) {
        int numCorrect = 0;
        float score;
        String[] correct = new String[num];
        HashMap<String, T> questions = question.generate(num);
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
                System.out.println("Questions Answered: " + (i + 1) + " of " + num);
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
                System.out.println("Questions Answered: " + (i + 1) + " of " + num);
                System.out.println();
            }
        }
        score = numCorrect / (float) num * 100;
        System.out.println("Your score is " + score);
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        DBHelper.insert("main", score, question.getClass().getSimpleName(), new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date()), gson.toJson(questions, type), gson.toJson(correct));
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
