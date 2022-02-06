package com.sergax.crudjdbc.utils;

import java.sql.*;

public class ConnectionWithDb {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_jdbc";

    static final String USER = "user";
    static final String PASSWORD = "Aks-662828";

    private Connection connection = null;
    public static ConnectionWithDb connectionWithDb = null;

    private ConnectionWithDb() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("SQL Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionWithDb getInstance() {
        return connectionWithDb == null ? new ConnectionWithDb() : connectionWithDb;
    }

    public Connection getConnection() {
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String SQL) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getInstance().
                    getConnection().
                    prepareStatement(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
}
