package com.uniovi.controllers;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.RedSocialApplication;
import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	static Logger log = LoggerFactory.getLogger(RedSocialApplication.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	

	@Autowired
	private RolesService rolesService;



	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}


	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			log.error("Error en el formulario de registro");
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);

		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		log.info("Accediendo al sistema via registro");
		return "redirect:home";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value= {"/user/list"},  method = RequestMethod.GET)
	public String getListado(Model model,Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUserByNameOrEmail(pageable,searchText);//TODO
		} 
		else {
			users = usersService.getUsersExceptMe(pageable, activeUser.getId());
			
		}
		model.addAttribute("page", users);
		model.addAttribute("usersList", users.getContent());
		log.info("Viendo la lista de usuarios para:" +activeUser.getFullName());


		return "user/list";
	}


	
	


}