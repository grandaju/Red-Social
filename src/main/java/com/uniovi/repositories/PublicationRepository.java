package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Publication;
@Repository
public interface PublicationRepository  extends CrudRepository<Publication, Long>{
	@Query("SELECT p FROM Publication p WHERE p.user.id = ?1 ORDER BY p.date DESC")
	Page<Publication> searchMyPublications(Pageable pageable, long id);

}
