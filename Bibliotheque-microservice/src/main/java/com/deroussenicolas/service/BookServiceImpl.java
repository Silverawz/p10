package com.deroussenicolas.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.entities.WaitingListReservation;

@Service("BookServiceImpl")
public class BookServiceImpl implements BookService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CopyService copyService;
	@Autowired
	private WaitingListReservationRepository waitingListReservationRepository;
	
	@Override
	public List<Book> getAllBooksForEmail() {
		List<Book> BooksForEmail = new ArrayList<>();

		LocalDate todayDate = LocalDate.now(ZoneId.of("Europe/Paris"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String todayDateFormated = todayDate.format(formatter);

		String dayNow = todayDateFormated.split("\\.")[0];
		String monthNow = todayDateFormated.split("\\.")[1];
		String yearNow = todayDateFormated.split("\\.")[2];
		int dayNowAsInt = Integer.parseInt(dayNow);
		int monthNowAsInt = Integer.parseInt(monthNow);
		int yearNowAsInt = Integer.parseInt(yearNow);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);

		List<Reservation> allReservationList = reservationRepository.reservationListNotArchived(false);

		for (Reservation reservation : allReservationList) {
			String dateEnd = reservation.getDate_end();
			int dayOfDateEnd = Integer.parseInt(dateEnd.split("\\.")[0]);
			int monthOfDateEnd = Integer.parseInt(dateEnd.split("\\.")[1]);
			int yearOfDateEnd = Integer.parseInt(dateEnd.split("\\.")[2]);
			// check the year
			if (yearNowAsInt >= yearOfDateEnd) {
				// check the month if year are equals
				if (yearNowAsInt == yearOfDateEnd && monthNowAsInt >= monthOfDateEnd) {
					// both year and month are equals
					if (monthNowAsInt == monthOfDateEnd) {
						// actual day is inferior to day end so send an email
						if (dayNowAsInt > dayOfDateEnd) {
							// RELANCE EMAIL ICI, A AJOUTER A LA LISTE (lemail de l'user)
							if (!BooksForEmail.contains(reservation.getCopy().getBook())) {
								BooksForEmail.add(reservation.getCopy().getBook());
							}
						}
					}
					// actual month is inferior to month end so send an email
					else if (monthNowAsInt > monthOfDateEnd) {
						// RELANCE EMAIL ICI, A AJOUTER A LA LISTE (lemail de l'user)
						if (!BooksForEmail.contains(reservation.getCopy().getBook())) {
							BooksForEmail.add(reservation.getCopy().getBook());
						}
					}
				}
				// year are not equals and current is inferior so sending an email
				else if (yearNowAsInt > yearOfDateEnd) {
					// RELANCE EMAIL ICI, A AJOUTER A LA LISTE (lemail de l'user)
					if (!BooksForEmail.contains(reservation.getCopy().getBook())) {
						BooksForEmail.add(reservation.getCopy().getBook());
					}
				}
			}
		}
		return BooksForEmail;
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book findById(int id) {
		return bookRepository.findById(id);
	}

	
	
	@Override
	public List<Integer> queueSizeForEachBooks() {
		List<Integer> queueSizeForEeachBooks = new ArrayList<>();
		List<Book> listAllBooks = bookRepository.findAll();
		
		List<WaitingListReservation> listWaitingListReservation = waitingListReservationRepository.waitingListReservationWithParams(false, false);
		
		//recuperer le nombre de copy pour chaque book		
		//copyService.numberOfCopiesNotAvailableForEachBook(); // *2 to respect the rules
		
		
		
		//calculer le nombre de personne dans la liste
		for (Book book : listAllBooks) {
			int incrementalNumber = 0;
			for (WaitingListReservation waitingListReservation : listWaitingListReservation) {
				if(waitingListReservation.getBook().getId_book() == (book.getId_book())) { 
					incrementalNumber++;
				}
			}
			queueSizeForEeachBooks.add(incrementalNumber);
		}
		return queueSizeForEeachBooks;
	}

	
	
	public void batchBook() {
		List<Book> bookList = bookRepository.findAll();
		List<WaitingListReservation> waitingListReservationToSendEmailForEachBook = new ArrayList<>();
		for (Book book : bookList) {
			//Pour chaque livre : 
			//si exemplaires disponibles > 0 
			List<Copy> copyAvailableByBookId = copyService.findCopiesAvailableByBookId('0', book.getId_book());
			
			
			//AND liste des réservations > 0
			List<WaitingListReservation> waitingListForTheBook = waitingListReservationRepository.waitingListReservationOfBookWithParams(book.getId_book(), false, false);
			
			if(copyAvailableByBookId.size() > 0 && waitingListForTheBook.size() > 0) {		
				A : for (WaitingListReservation waitingListReservation : waitingListForTheBook) {
					if (waitingListReservation.getPosition_in_queue() == 1) {
						if (waitingListReservation.getDate_mail_send() == null) {
							//envoyer le mail et rajouter la date d'envoi
							waitingListReservation.setDate_mail_send(new Date());
							waitingListReservationRepository.save(waitingListReservation);
							waitingListReservationToSendEmailForEachBook.add(waitingListReservation);
							break A;
						} else if (waitingListReservation.getDate_mail_send() != null) {
							//check if date is 48 hour old past, if yes then delete the waitingListReservation
							boolean verificationDate = compareDateOfWaitingListReservation(waitingListReservation.getDate_mail_send());
							if(!verificationDate) {
								waitingListReservationRepository.delete(waitingListReservation);
								
								//date mail < 48H
								/*
								alors
								supprimer réservation
								modifier position dans la liste
								envoyer mail au nouveau position 1
								*/
							} else {
								//date mail >= 48H
								// do nothing
							}
						}
					}
				}
			}
		}
	}
	
	
	// 12
	// verifier que le 14 ne soit pas depassé
	
	
	
	public boolean compareDateOfWaitingListReservation(Date date) {
		boolean result = false;		
		Calendar calendarOfArgumentDate = Calendar.getInstance(); 
		calendarOfArgumentDate.setTime(date);
		calendarOfArgumentDate.add(Calendar.HOUR_OF_DAY, 48); // adds 48 hour	
		Calendar calendarActualDateToday = Calendar.getInstance();
		calendarActualDateToday.setTime(new Date());			
		if (calendarOfArgumentDate.compareTo(calendarActualDateToday) > 0) {
			result = true;
		} 
		return result;
	}
	
	
}
