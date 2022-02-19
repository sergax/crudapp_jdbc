package com.sergax.crudjdbc.utils;

import java.sql.*;

public class ConnectionWithDb {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_jdbc";

    static final String USER = "user";
    static final String PASSWORD = "Aks-662828";

    // + CrudProperties.get
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(
                    (DATABASE_URL),
                    (USER),
                    (PASSWORD));
        } catch (SQLException e) {
            System.out.println("SQL Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
