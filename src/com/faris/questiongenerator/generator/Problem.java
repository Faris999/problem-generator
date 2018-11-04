package com.faris.questiongenerator.generator;

import java.util.HashMap;

public abstract class Problem<V> {

    public abstract HashMap<String, V> generate(int num);

}
