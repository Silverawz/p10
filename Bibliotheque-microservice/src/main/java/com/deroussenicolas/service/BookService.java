package com.deroussenicolas.service;

import java.util.List;

import com.deroussenicolas.entities.Book;

public interface BookService {
	
	List<Book> getAllBooksForEmail();

	List<Book> findAll();

	Book findById(int id);
}
