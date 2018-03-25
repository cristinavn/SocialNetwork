package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.repositories.InvitationRepository;

@Service
public class InvitationService {
	@Autowired
	private InvitationRepository invitationRepository;

	private static final Logger logger = LoggerFactory.getLogger(InvitationService.class);
	
	public void addPeticion(Invitation peticion) {
		invitationRepository.save(peticion);
		logger.debug(String.format("Invitation from %s to %s sent successfully!",
				peticion.getEnviada().getEmail(), peticion.getRecibida().getEmail()));
	}

	public Invitation find(Long id) {
		return invitationRepository.findOne(id);
	}

	public void delete(Invitation invitation) {
		invitationRepository.delete(invitation);
	}
	

}
