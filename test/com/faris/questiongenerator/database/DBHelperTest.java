package com.faris.questiongenerator.database;

import com.google.gson.Gson;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBHelperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test1createTable() {
        //noinspection ResultOfMethodCallIgnored
        new File("res/test.db").delete();
        DBHelper.createNewTable("test");
    }

    @Test
    public void test2insert() {
        Gson gson = new Gson();
        DBHelper.insert("test", 100, "Integers", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date()), "", gson.toJson(new String[]{"1", "2", "3", "4", "5"}));
    }

    @Test
    public void test3select() throws SQLException {
        Gson gson = new Gson();
        ResultSet rs = DBHelper.select("test", "SELECT * FROM score");
        while (rs.next()) {
            assertEquals(100, rs.getFloat("score"), .5);
            assertEquals("Integers", rs.getString("subject"));
            assertEquals(gson.toJson(new String[]{"1", "2", "3", "4", "5"}), rs.getString("correct"));
        }
    }

    @Test()
    public void test4selectWrongColumn() throws SQLException {
        ResultSet rs = DBHelper.select("test", "SELECT * FROM score");
        thrown.expect(SQLException.class);
        rs.getFloat("sfgdsa");
    }

}