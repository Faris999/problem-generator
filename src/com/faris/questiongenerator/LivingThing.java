package com.faris.questiongenerator;

import java.util.HashMap;

public class LivingThing {

    private String name;
    private HashMap<String, String> characteristics;

    public LivingThing(String name, HashMap<String, String> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getCharacteristics() {
        return characteristics;
    }

}
