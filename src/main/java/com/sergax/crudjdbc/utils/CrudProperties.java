package com.sergax.crudjdbc.utils;

import java.io.IOException;
import java.util.Properties;

public class CrudProperties {
    private static final Properties PROPERTIES = new Properties();
    CrudProperties crudProperties = new CrudProperties();

    static {
        try {
            getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CrudProperties() {

    }

    public static  String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static void getProperties() throws IOException {
        try (var inputStream = CrudProperties.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
