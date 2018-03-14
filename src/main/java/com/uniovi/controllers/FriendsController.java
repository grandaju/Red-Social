package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Friend;
import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.FriendService;
import com.uniovi.services.InvitationService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {
	
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private FriendService friendService;
	
	/**
	 * Metodo que permite llamar a las acciones relativas para agregar a un amigo
	 * en eset caso crea una entrada que relaciona dos usuarios
	 * @param 
	 * @param id de la invitacion
	 * @return
	 */
	@RequestMapping(value = "/friend/agergate/{id}")
	public String agregate(Model model, @PathVariable Long id) {
		//Obtenemos la invitacion y los integrantes
		Invitation invit = invitationService.getInvitation(id);
		User userA = invit.getInvitado();
		User userB = invit.getInvitador();
		//Creamos una amistad
		friendService.agregate(userA, userB);
		friendService.agregate(userB, userA);
		//Eliminamos la invitacion
		invitationService.remove(id);
		return "redirect:/user/list/";
	}
	/**
	 * Metodo que realiza la llamada para obtener la lista de amigos de un usuario
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/friends/list")
	public String getFriends(Model model, Pageable pageable) {
		Page<User> invis = new PageImpl<User>(new LinkedList<User>());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);

		invis = friendService.getFriends(pageable, user.getId());
		
	
	
		model.addAttribute("page", invis);
		model.addAttribute("friendList", invis.getContent());

		return "/friends/list";
	}
}