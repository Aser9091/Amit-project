package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demoblaze Test Automation Framework ===");
        System.out.println("Version: 1.0-SNAPSHOT");
        System.out.println("Target Environment: https://www.demoblaze.com/");
        
        // Load and display configuration
        try {
            Properties props = new Properties();
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config/test.properties");
            if (input != null) {
                props.load(input);
                System.out.println("Default Browser: " + props.getProperty("browser", "chrome"));
                System.out.println("Implicit Wait: " + props.getProperty("implicit.wait", "10") + " seconds");
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load test properties");
        }
        
        System.out.println("\nTo run tests: mvn clean test");
        System.out.println("For smoke tests: mvn test -Dcucumber.filter.tags=\"@smoke\"");
    }
}