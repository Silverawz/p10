package com.deroussenicolas.service;

import java.util.Date;
import java.util.List;

import com.deroussenicolas.entities.Book;

public interface BookService {
	
	List<Book> getAllBooksForEmail();

	List<Book> findAll();

	Book findById(int id);

	List<Integer> queueSizeForEachBooks();
	
	boolean compareDateOfWaitingListReservation(Date date);
}
