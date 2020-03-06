package com.deroussenicolas.service;

import java.util.List;

import com.deroussenicolas.entities.Copy;

public interface CopyService {

	List<Copy> findAll();

	Copy findById(int id);

	List<Copy> findCopiesAvailable(char c);

	void save(Copy copyOfTheReservation);

}
