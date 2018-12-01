package com.faris.questiongenerator.main;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

public class MainTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private PrintStream old = System.out;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    public void cleanUp() {
    }

    @Test
    public void test() throws IOException {
        String data = "answer\nPGL";
        InputStream testInput = new ByteArrayInputStream(data.getBytes("UTF-8"));
        InputStream oldIn = System.in;
        boolean thrown = false;
        try {
            System.setIn(testInput);
            new Main();
        } catch (NoSuchElementException e) {
            thrown = true;
        } finally {
            System.setIn(oldIn);
        }
        assertTrue(thrown);
        System.setOut(old);
        System.out.println(out.toString());
    }

}