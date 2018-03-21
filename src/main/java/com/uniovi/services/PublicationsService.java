package com.uniovi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationsService {

	@Autowired
	private PublicationRepository publicationRepo;
	
	public void agregate(User user, Publication publication) {
		publication.setUser(user);
		publication.setDate(new Date());
		publicationRepo.save(publication);
	}
	/**
	 * Metodo que obtiene las publicaciones en funcion del id de usuario
	 * @param pageable
	 * @param id del usuario para obtener sus publicaciones
	 * @return
	 */
	public Page<Publication> getPublications(Pageable pageable, long id) {
		Page<Publication> pubs = publicationRepo.searchPublications(pageable, id);
		return pubs;
	}

}
