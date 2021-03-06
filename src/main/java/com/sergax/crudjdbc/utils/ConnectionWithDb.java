package com.sergax.crudjdbc.utils;

import java.sql.*;

public class ConnectionWithDb {
    /**
     * MySQL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_jdbc";
    static final String USER = "user";
    static final String PASSWORD = "Aks-662828";
     */

    static final String DATABASE_URL_PostSQL = "jdbc:postgresql://localhost:5432/user_db";
    static final String USER_PostSQL = "info_user";
    static final String PASSWORD_PostSQL  = "user";

    public static Connection getConnection() {
        Connection connection = null;

        try {
//            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(
                    (DATABASE_URL_PostSQL),
                    (USER_PostSQL),
                    (PASSWORD_PostSQL));
        } catch (SQLException e) {
            System.out.println("SQL Error");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return connection;
    }
}
