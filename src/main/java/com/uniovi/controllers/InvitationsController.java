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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.RedSocialApplication;
import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationService;
import com.uniovi.services.UsersService;

@Controller
public class InvitationsController {
	static Logger log = LoggerFactory.getLogger(RedSocialApplication.class);

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationService invitationService;
	/**
	 * Respuesta para agregar a un amigo obtenemos nuestra propia info a traves del contexto
	 * @param model
	 * @param id de quien queremos agregar
	 * @return
	 */
	@RequestMapping(value = "/invitation/agergate/{id}")
	public String agregate(Model model, @PathVariable Long id) {
		User invitado = usersService.getUser(id);
		if(invitado == null) {
			return "redirect:/user/list";
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User invitador = usersService.getUserByEmail(email);
		invitationService.agregate(invitado, invitador);
		log.info("Enviada la invitacion a:"+invitado.getFullName() +" de: "+invitador.getFullName());
		return "redirect:/user/list/";
	}
	/**
	 * Metodo que muestra las invitaciones del usuario conectado
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/invitation/list")
	public String getInvitations(Model model, Pageable pageable) {
		Page<Invitation> invis = new PageImpl<Invitation>(new LinkedList<Invitation>());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);

		invis = invitationService.getInvitations(pageable, user.getId());
		
	
	
		model.addAttribute("page", invis);
		model.addAttribute("invitationsList", invis.getContent());

		log.info("Listando invitaciones de:"+user.getFullName());
		return "/invitation/list";
	}
	
	@RequestMapping(value = "/invitation/list/update")
	public String getInvitationsFrag(Model model, Pageable pageable) {
		Page<Invitation> invis = new PageImpl<Invitation>(new LinkedList<Invitation>());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);

		invis = invitationService.getInvitations(pageable, user.getId());
		
	

		model.addAttribute("page", invis);
		model.addAttribute("invitationsList", invis.getContent());

		log.info("Listando invitaciones de:"+user.getFullName());
		return "/invitation/list :: tableInvis";
	}
}


