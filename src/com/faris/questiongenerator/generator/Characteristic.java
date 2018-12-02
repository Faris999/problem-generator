package com.faris.questiongenerator.generator;

import java.util.regex.Pattern;

public class Characteristic {

    private String name;
    private String function;
    private boolean isNoun;
    private boolean startsWithVocal;

    public Characteristic(String name, String function, boolean isNoun) {
        this.name = name;
        this.function = function;
        this.isNoun = isNoun;
        this.startsWithVocal = Pattern.compile("aiueo").matcher("name").matches();
    }

    String getName() {
        return name;
    }

    String getFunction() {
        return function;
    }

    boolean isNoun() {
        return isNoun;
    }

    boolean startsWithVocal() {
        return startsWithVocal;
    }
}
