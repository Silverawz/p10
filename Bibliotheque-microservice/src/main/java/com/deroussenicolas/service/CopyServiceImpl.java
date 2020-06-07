package com.deroussenicolas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.entities.Copy;

@Service("CopyServiceImpl")
public class CopyServiceImpl implements CopyService {

	@Autowired
	private CopyRepository copyRepository;
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Copy> findAll() {
		return copyRepository.findAll();
	}

	@Override
	public Copy findById(int id) {
		return copyRepository.findById(id);
	}

	@Override
	public List<Copy> findCopiesAvailable(char c) {
		return copyRepository.findCopiesAvailable(c);
	}

	@Override
	public void save(Copy copyOfTheReservation) {
		copyRepository.save(copyOfTheReservation);	
	}

	@Override
	public List<Integer> numberOfCopies() {
		List<Integer> numberOfCopiesForEachBooks = new ArrayList<>();
		int numberOfBooks = bookRepository.findAll().size();
		List<Copy> copiesListNotAvailable = new ArrayList<>();
		copiesListNotAvailable.addAll(copyRepository.findCopiesAvailable('1'));
		copiesListNotAvailable.addAll(copyRepository.findCopiesAvailable('2'));

		for (int i = 0 ; i < numberOfBooks ; i++) {
			int incrementalNumber = 0;
			for (Copy copy : copiesListNotAvailable) {

			}		
		}
	
		return numberOfCopiesForEachBooks;
	}

}
