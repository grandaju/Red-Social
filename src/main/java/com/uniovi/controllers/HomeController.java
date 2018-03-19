package com.uniovi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.RedSocialApplication;

@Controller
public class HomeController {
	static Logger log = LoggerFactory.getLogger(RedSocialApplication.class);

	@RequestMapping("/" )
		public String index() {
		log.info("Accediendo a home");
		return "index";
	}
}
