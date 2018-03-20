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
	 * Obtenemos los amigos que se encuentran relacionados con el id del usuario
	 * @param pageable
	 * @param id
	 * @return
	 */
	public Page<User> getFriends(Pageable pageable, long id) {
		Page<User> invis = friendRepository.searchByAmistad(pageable, id);
		return invis;
	}

	/**
	 * Metodo que confirma la existencia de una amistad
	 * @param id
	 * @param id2
	 * @return
	 */
	public boolean checkFriendShip(long id, long id2) {
		Friend amistad = friendRepository.searchByUsers(id, id2);
		if(amistad != null) {
			return true;
		}
		return false;
	}
}
