package com.agh.eventarz2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class. Simply fires the Spring run() method.
 */
@SpringBootApplication
public class Eventarz2Application {

    private final static Logger log = LoggerFactory.getLogger(Eventarz2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Eventarz2Application.class, args);
    }
}
