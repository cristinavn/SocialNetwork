package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Peticion;

public interface PeticionRepository extends CrudRepository<Peticion, Long> {

}
