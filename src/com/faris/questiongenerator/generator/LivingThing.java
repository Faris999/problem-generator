package com.faris.questiongenerator.generator;

import java.util.ArrayList;

public class LivingThing {

    private String name;
    private ArrayList<Characteristic> characteristics;

    public LivingThing(String name, ArrayList<Characteristic> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
    }

    String getName() {
        return name;
    }

    ArrayList<Characteristic> getCharacteristics() {
        return characteristics;
    }

}
