package com.uniovi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedSocialApplication {
	static Logger log = LoggerFactory.getLogger(RedSocialApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RedSocialApplication.class, args);
		log.info("Inciando aplicacion");
	}
}
