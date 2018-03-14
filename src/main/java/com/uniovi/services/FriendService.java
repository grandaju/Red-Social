package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRepository;

@Service
public class FriendService {
	
	@Autowired
	private FriendRepository friendRepository;
	
	/**
	 *  Creamos la amistad y la agregamos a la base de datos
	 */
	public void agregate(User userA, User userB) {
		Friend amistad = new Friend (userA,userB);
		friendRepository.save(amistad);
	}
	
	/**
	 * Metodo que nos devuelve la pagina con las amistades
	 * @param pageable
	 * @param id del usuario conectado
	 * @return
	 */
	public Page<User> getInvitations(Pageable pageable, long id) {
		Page<User> invis = friendRepository.searchByAmistad(pageable, id);
		return invis;
	}
}
