package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Friend;
import com.uniovi.entities.User;
@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
	/**
	 * Busqueda de un usuario del cual se es amigo
	 * @param pageable
	 * @param id
	 * @return
	 */
	@Query("Select f.userB from Friend f where f.userA.id = ?1")
	Page<User> searchByAmistad(Pageable pageable, long id);

	Page<User> findAll(Pageable pageable);
	
	/**
	 * Metodo que permite confirmar que existe la amistad entre dos usuarios
	 * @param pageable
	 * @param id
	 * @return
	 */
	@Query("Select f from Friend f where f.userA.id = ?1 and f.userB.id = ?2")
	Friend searchByUsers(long id1, long id2);
}
