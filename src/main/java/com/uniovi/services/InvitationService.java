package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.repositories.InvitationRepository;

@Service
public class InvitationService {
	@Autowired
	private InvitationRepository invitationRepository;

	public void addPeticion(Invitation peticion) {
		invitationRepository.save(peticion);
	}

	public Invitation find(Long id) {
		return invitationRepository.findOne(id);
	}

	public void delete(Invitation invitation) {
		invitationRepository.delete(invitation);
	}
	

}
