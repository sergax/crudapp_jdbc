package com.sergax.crudjdbc.utils;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor
public class CrudProperties {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String get(String query) {
        return PROPERTIES.getProperty(query);
    }

    private static void getProperties() throws IOException {
        try (var inputStream = CrudProperties.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
