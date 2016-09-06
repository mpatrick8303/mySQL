package org.ssa.ironyard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarter {
    static final Logger LOGGER = LogManager.getLogger(SpringStarter.class);
    public static void main (String[] args)
    {
        LOGGER.info("Starting web app");
        SpringApplication.run(SpringStarter.class, args);
    }
}

