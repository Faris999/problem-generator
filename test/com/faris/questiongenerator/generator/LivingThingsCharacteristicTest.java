package com.faris.questiongenerator.generator;

import com.faris.questiongenerator.generator.LivingThingsCharacteristic;
import org.junit.Test;

public class LivingThingsCharacteristicTest {

    @Test
    public void generate() {
        new LivingThingsCharacteristic().generate(6);
    }
}