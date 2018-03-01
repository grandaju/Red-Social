package com.uniovi.repositories;
import com.uniovi.entities.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long>{
	
	User findByDni(String dni);
	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR	LOWER(u.dni) LIKE LOWER(?1))")
	Page<User> searchByNameOrDni(Pageable pageable,String searchText);

	Page<User> findAll(Pageable pageable);

	
}