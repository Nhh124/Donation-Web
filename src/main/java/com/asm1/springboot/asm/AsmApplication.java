package com.asm1.springboot.asm;

import ch.qos.logback.core.model.Model;
import com.asm1.springboot.asm.controller.DonationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
public class AsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsmApplication.class, args);
	}

}
