package com.faris.questiongenerator.database;

import java.sql.*;

public class DBHelper {

    private static Connection connect(String name) {
        // SQLite connection string
        String url = "jdbc:sqlite:res/" + name + ".db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static ResultSet select(String name, String command) {
        Connection conn = DBHelper.connect(name);
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void insert(String name, float score, String subject, String timestamp, String questions, String correct) {
        String sql = "INSERT INTO score(score,subject,date,questions,correct) VALUES(?,?,?,?,?)";

        try (Connection conn = DBHelper.connect(name);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, score);
            pstmt.setString(2, subject);
            pstmt.setString(3, timestamp);
            pstmt.setString(4, questions);
            pstmt.setString(5, correct);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createNewTable(String name) {
        // SQLite connection string
        String url = "jdbc:sqlite:res/" + name + ".db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS score (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	score real NOT NULL,\n"
                + "	subject text NOT NULL,\n"
                + " date text NOT NULL,\n"
                + " questions text NOT NULL,\n"
                + " correct text NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
