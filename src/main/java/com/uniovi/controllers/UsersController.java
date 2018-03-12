package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.entities.*;
import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddUserValidator;
import com.uniovi.validators.LoginValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private AddUserValidator addUserValidator;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	// @RequestMapping(value = "/signup", method = RequestMethod.POST)
	// public String signup(@ModelAttribute("user") User user, Model model) {
	// usersService.addUser(user);
	// securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
	// return "redirect:home";
	// }
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);

		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		return "home";
	}

	@RequestMapping(value= {"/user/list"},  method = RequestMethod.GET)
	public String getListado(Model model,Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUserByNameOrEmail(pageable,searchText);//TODO
		} 
		else {
			users = usersService.getUsers(pageable);
			
		}
		model.addAttribute("page", users);
		model.addAttribute("usersList", users.getContent());

		return "user/list";
	}

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());

		model.addAttribute("user", new User());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addUser(@Validated User user, BindingResult result, Model model) {

		addUserValidator.validate(user, result);
		if (result.hasErrors()) {
			return "/user/add";
		}
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		User user = usersService.getUser(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
		User original = usersService.getUser(id);
		original.setName(user.getName());
		original.setLastName(user.getLastName());
		usersService.addUser(original);
		return "redirect:/user/details/" + id;
	}
	
	
//	/**
//	 * Respuesta para agregar a un amigo obtenemos nuestra propia info a traves del contexto
//	 * @param model
//	 * @param id de quien queremos agregar
//	 * @return
//	 */
//	@RequestMapping(value = "/user/agregate/{id}")
//	public String agregate(Model model, @PathVariable Long id) {
//		User invitado = usersService.getUser(id);
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String email = auth.getName();
//		User invitador = usersService.getUserByEmail(email);
//		usersService.agregate(invitado, invitador);
//		return "redirect:/user/list/";
//	}

}