package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.repositories.InvitationRepository;

@Service
public class InvitationService {

	@Autowired
	private InvitationRepository invitationRepository;
	/**
	 * Se crea y añade la invitacion a la base de datos
	 * @param invitado
	 * @param invitador
	 */
	public void agregate(User invitado, User invitador) {
		Invitation i = new Invitation(invitado,invitador,false);
		invitationRepository.save(i);
	}
	
	/**
	 * Metodo que nos devuelve la pagina con las invitaciones
	 * @param pageable
	 * @param id del usuario conectado
	 * @return
	 */
	public Page<Invitation> getInvitations(Pageable pageable, long id) {
		Page<Invitation> invis = invitationRepository.searchByInvitado(pageable, id);
		return invis;
	}
	/**
	 * Busqueda de una invitacion mediante los id de los usuarios relacionados
	 * @param id
	 * @param userId
	 * @return
	 */
	public Invitation getInvitation(long id, long userId) {
		return invitationRepository.serarchByIds(id, userId);
	}

	public void remove(Long id) {
		invitationRepository.delete(id);
		
	}

}
