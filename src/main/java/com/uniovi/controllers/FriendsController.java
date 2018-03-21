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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.FriendService;
import com.uniovi.services.InvitationService;
import com.uniovi.services.UsersService;

@Controller
public class FriendsController {
	
	static Logger log = LoggerFactory.getLogger(FriendsController.class);

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private FriendService friendService;
	
	/**
	 * Metodo que permite llamar a las acciones relativas para agregar a un amigo
	 * en eset caso crea una entrada que relaciona dos usuarios se obtiene la invitacion segun los id de los usuarios
	 * @param 
	 * @param id del invitador
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/friend/agergate/{id}")
	public String agregate(Model model, @PathVariable long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		
		Invitation invit = invitationService.getInvitation(id, user.getId());
		User userA = invit.getInvitado();
		User userB = invit.getInvitador();
		
		invitationService.remove(invit.getId());
		
		friendService.agregate(userA, userB);
		friendService.agregate(userB, userA);

		log.info("Se ha añadido la amistad" + userA.getFullName() +" y "+ userB.getFullName());
		return "redirect:/invitation/list";
	}
	/**
	 * Metodo que realiza la llamada para obtener la lista de amigos de un usuario
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/friends/list")
	public String getFriends(Model model, Pageable pageable) {
		Page<User> amigos = new PageImpl<User>(new LinkedList<User>());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);

		amigos = friendService.getFriends(pageable, user.getId());
		
	
	
		model.addAttribute("page", amigos);
		model.addAttribute("friendList", amigos.getContent());
		
		log.info("Listando los amigos de:"+ user.getFullName());

		return "/friends/list";
	}
}
