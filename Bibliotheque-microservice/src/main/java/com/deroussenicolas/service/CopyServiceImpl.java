package com.deroussenicolas.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.entities.Book;
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
	public List<Integer> numberOfCopiesNotAvailableForEachBook() {
		List<Integer> numberOfCopiesNotAvailableForEachBooks = new ArrayList<>();
		List<Book> listAllBooks = bookRepository.findAll();
		List<Copy> copiesListNotAvailable = new ArrayList<>();
		copiesListNotAvailable.addAll(copyRepository.findCopiesAvailable('1'));
		copiesListNotAvailable.addAll(copyRepository.findCopiesAvailable('2'));			
		for (int i = 0 ; i < listAllBooks.size() ; i++) {
			int numberOfCopiesPerBook = 0;
			for (Copy copy : copiesListNotAvailable) { 
				if (listAllBooks.get(i).getId_book() == (copy.getBook().getId_book())) {
					numberOfCopiesPerBook++;
				}
			}
			// must be multiplied by 2 to get the maximum reservation number that a book can have
			numberOfCopiesNotAvailableForEachBooks.add(numberOfCopiesPerBook*2);
		}	
		return numberOfCopiesNotAvailableForEachBooks; 
	}

}
