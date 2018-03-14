package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Friend;
import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
	
	@Query("Select f.userB from Friend f where f.userA.id = ?1")
	Page<User> searchByAmistad(Pageable pageable, long id);

	Page<User> findAll(Pageable pageable);
}
