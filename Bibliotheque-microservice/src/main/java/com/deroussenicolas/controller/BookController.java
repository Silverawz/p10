package com.deroussenicolas.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.service.BookService;
import com.deroussenicolas.service.ReservationService;
import com.deroussenicolas.service.UserService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/Books")
    public @ResponseBody List<Book> listOfAllBooks() {
		List<Book> listBook = bookService.findAll();
        return listBook;
    }

	@GetMapping(value="/BooksToEmail")
    public @ResponseBody List<Book> listOfAllBooksToSendEmail() {
		List<Book> listBook = bookService.getAllBooksForEmail();
        return listBook;
    }
	
    @GetMapping(value = "/Book/{id}")
    public Book oneBookWithId(@PathVariable int id) {		
    	return bookService.findById(id);
    }
    
    @GetMapping(value = "/BooksOfUser/{email}")
    public List<Book> allBookWithUserEmail(@PathVariable String email) {
    	List <Reservation> reservationsList = reservationService.reservationListOfUser(userService.findByEmail(email).getId_user());
    	List <Book> booksList = new ArrayList<>();
    	for (Reservation reservation : reservationsList) {
    		booksList.add(reservation.getCopy().getBook());
		}	
    	return booksList;
    }

}
