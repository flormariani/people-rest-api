package com.fmariani.examples.crudPerson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Created by florencia on 08/06/21.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
public class Application
{
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
