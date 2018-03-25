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

	public void addPeticion(Invitation peticion) {
		invitationRepository.save(peticion);
	}

	public Invitation find(Long id) {
		return invitationRepository.findOne(id);
	}

	public void delete(Invitation invitation) {
		invitationRepository.delete(invitation);
	}

	public void sendInvitation(User activeUser, User enviar) {
		Invitation peticion = new Invitation(activeUser,enviar);
		addPeticion(peticion);
		activeUser.getEnviadas().add(peticion);
		enviar.getRecibidas().add(peticion);
	}

	public Page<Invitation> getInvitationsReceibed(String email, Pageable pageable) {
		Page<Invitation> invitations = invitationRepository.getInvitationsReceibed(email,pageable);
		return invitations;
	}
	
}
