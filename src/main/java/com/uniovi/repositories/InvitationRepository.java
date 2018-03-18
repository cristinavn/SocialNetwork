package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invitation;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {

}
