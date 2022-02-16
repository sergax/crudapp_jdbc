package com.sergax.crudjdbc.utils;

import java.sql.*;

public class ConnectionWithDb {
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_jdbc";
//
//    static final String USER = "user";
//    static final String PASSWORD = "Aks-662828";


//    public static ConnectionWithDb connectionWithDb = null;

//    private ConnectionWithDb() {
//        try {
////            Properties properties = crudProperty.getProperties();
//            Class.forName(JDBC_DRIVER);
//            connection = DriverManager.getConnection(CrudProperties.get(DATABASE_URL),
//                    CrudProperties.get(USER),
//                    CrudProperties.get(PASSWORD));
//        } catch (SQLException e) {
//            System.out.println("SQL Error");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static ConnectionWithDb getInstance() {
//        return connectionWithDb == null ? new ConnectionWithDb() : connectionWithDb;
//    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
//            Properties properties = crudProperty.getProperties();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CrudProperties.get("jdbc.url"),
                    CrudProperties.get("jdbc.user"),
                    CrudProperties.get("jdbc.pass"));
        } catch (SQLException e) {
            System.out.println("SQL Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    public static PreparedStatement getPreparedStatement(String SQL) {
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = getInstance().
//                    getConnection().
//                    prepareStatement(SQL);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return preparedStatement;
//    }
}
