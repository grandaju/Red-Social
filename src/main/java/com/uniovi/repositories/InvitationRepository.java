package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Invitation;
@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {
	@Query("Select i from Invitation i where i.invitado.id = ?1")
	Page<Invitation> searchByInvitado(Pageable pageable, long id);
	@Query("Select i from Invitation i where i.invitador.id =?1 and i.invitado.id = ?2")
	Invitation serarchByIds(long id, long userId);

}
