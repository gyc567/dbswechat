package com.dbs.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by eric567 [email:gyc567@126.com]
 * on 11/14/2016.
 */
public class H2DBStoreUtil {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static Timestamp getCurrentTimestamp() {
        // 1) create a java calendar instance
        Calendar calendar = Calendar.getInstance();

        // 2) get a java.util.Date from the calendar instance.
        //    this date will represent the current instant, or "now".
        java.util.Date now = calendar.getTime();

        // 3) a java current time (now) instance
        Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return currentTimestamp;
    }
}
