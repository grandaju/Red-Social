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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.RedSocialApplication;
import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.FriendService;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;

@Controller
public class PublicationsController {
	static Logger log = LoggerFactory.getLogger(RedSocialApplication.class);

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private PublicationsService publicationsService;
	
	@RequestMapping(value = "/publication/create")
	public String getPublications(Model model) {
		model.addAttribute("pub", new Publication());
		return "/publication/create";
	}
	@RequestMapping(value = "/publication/create", method = RequestMethod.POST)
	public String agregate(@ModelAttribute Publication publication) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		publicationsService.agregate(user, publication);
		log.info("Creando una nueva publicacion");
		return "redirect:/publication/list/";
	}
	
	@RequestMapping(value= {"/publication/list"},  method = RequestMethod.GET)
	public String list(Model model,Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		
		publications = publicationsService.getPublications(pageable, user.getId());
			
		
		model.addAttribute("publicationsList", publications.getContent());
		
		log.info("Viendo las publicaciones de:" + user.getFullName());

		return "/publication/list";
	}

	
	@RequestMapping(value= {"/publication/list/{id}"},  method = RequestMethod.GET)
	public String listFirendPublicartions(Model model,Pageable pageable, @PathVariable long id) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		
		if(friendService.checkFriendShip(id,user.getId() )) {
			publications = publicationsService.getPublications(pageable, id);
				
			model.addAttribute("propietario", usersService.getUser(id));
			model.addAttribute("publicationsList", publications.getContent());
			
			log.info("Viendo las publicaciones de:" + usersService.getUser(id).getFullName());
	
			return "/publication/list";
		}
		
		log.error("No pueden visualizarse las publicaciones de "+ usersService.getUser(id).getFullName()+ ""
				+ " ya que no hay relacion de amistad creada" );

		
		return "redirect:/";
	}
	
}
