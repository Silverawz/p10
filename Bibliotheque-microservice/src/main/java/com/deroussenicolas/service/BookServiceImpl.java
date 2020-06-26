package com.deroussenicolas.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.SmtpMailSender;
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
	@Autowired
	private SmtpMailSender smtpMailSender;
	
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

	
	
	public List<WaitingListReservation> batchBook() {
		List<Book> bookList = bookRepository.findAll();
		List<WaitingListReservation> waitingListReservationToSendEmailForEachBook = new ArrayList<>();
		for (Book book : bookList) {
			//Pour chaque livre : 
			List<Copy> copyAvailableByBookId = copyService.findCopiesAvailableByBookId('0', book.getId_book());
			List<WaitingListReservation> waitingListForTheBook = waitingListReservationRepository.waitingListReservationOfBookWithParams(book.getId_book(), false, false);
			//si exemplaires disponibles > 0 AND liste des réservations > 0
			if (copyAvailableByBookId.size() > 0 && waitingListForTheBook.size() > 0) {
				//en return il doit y avoir un objet WaitingListReservation	ajouté à la liste		
				waitingListReservationToSendEmailForEachBook.add(algoBatch(waitingListForTheBook, book.getId_book()));
			}
		}
		return waitingListReservationToSendEmailForEachBook;
	}

	
	public WaitingListReservation algoBatch(List<WaitingListReservation> waitingListForTheBook, int book_id) {
		WaitingListReservation waitingListReservationReturned = new WaitingListReservation();
		int index_waitingListReservation_first_in_queue = checkTheFirstInTheQueueForWaitingListForTheBook(waitingListForTheBook);
		try {
			waitingListForTheBook.get(index_waitingListReservation_first_in_queue);
		} catch (Exception e) {
			return null;
		}
		WaitingListReservation waitingListReservation = waitingListForTheBook.get(index_waitingListReservation_first_in_queue);
		if (waitingListReservation.getDate_mail_send() == null) {
			//envoyer le mail et rajouter la date d'envoi
			sendingEmail(waitingListReservation);
			// TO DO smtpMailSender.send(to, subject, body);
			System.err.println("a reçu le mail = "+waitingListReservation.getId_waiting_list_reservation());
			Calendar cal = Calendar.getInstance();
		    cal.setTime(new Date());
		    cal.add(Calendar.HOUR_OF_DAY, 2);
		    Date date = cal.getTime();	    
			waitingListReservation.setDate_mail_send(date);
			waitingListReservationRepository.save(waitingListReservation);
			waitingListReservationReturned = waitingListReservation;
		} else if (waitingListReservation.getDate_mail_send() != null) {
			//check if date is 48 hour old past, if yes then delete the waitingListReservation
			boolean verificationDate = compareDateOfWaitingListReservation(waitingListReservation.getDate_mail_send());
			if(!verificationDate) {
				waitingListForTheBook.remove(waitingListReservation);
				waitingListReservationRepository.delete(waitingListReservation);
				// modifier position dans la liste
				int positionInTheQueue = 1;
				while (!waitingListForTheBook.isEmpty()) {
					waitingListForTheBook = recalculatePositionInTheQueueForTheWaitingListReservation(waitingListForTheBook, positionInTheQueue);
					positionInTheQueue++;
				}
				//repeter le batch pour trouver un nouveau 1er
				algoBatch(waitingListReservationRepository.waitingListReservationOfBookWithParams(book_id, false, false), book_id);
			} else {
				return null;
			}
		}
		return waitingListReservationReturned;
	}
	

	private void sendingEmail(WaitingListReservation waitingListReservation) {
		String userEmail = waitingListReservation.getUser().getEmail();
		String bookName = waitingListReservation.getBook().getBook_name();
		try {
			smtpMailSender.send(userEmail,   bookName + " is available", 
			"Hello, you have made a reservation for the book named : " + bookName + " . We are happy to announce you the book is available in the library. "
		    + "Your have 48 hours to claim the book otherwise your reservation will be canceled automatically. Have a nice day! "); 
		}
		catch (MessagingException e) {
			e.printStackTrace(); 
		}	
	}
	
	
	public List<WaitingListReservation> recalculatePositionInTheQueueForTheWaitingListReservation(List<WaitingListReservation> waitingListReservation, int positionInTheQueue) {
		List<WaitingListReservation> listToCompare = waitingListReservation;
		int id_current_WaitingListReservation_closest_to_zero = 0;
		int position_in_queue_closest_to_zero = 0;		
		int index_of_lowest_position = 0;
		if (listToCompare.size() >= 2) {
			for (int i = 0; i < listToCompare.size(); i++) {			
				int position_in_queue_forCurrentReservation = listToCompare.get(i).getPosition_in_queue();	
				if (position_in_queue_forCurrentReservation < position_in_queue_closest_to_zero || position_in_queue_closest_to_zero == 0) {	
					id_current_WaitingListReservation_closest_to_zero = listToCompare.get(i).getId_waiting_list_reservation();
					position_in_queue_closest_to_zero = position_in_queue_forCurrentReservation;
					index_of_lowest_position = i;
				}	
			}	
			listToCompare.remove(index_of_lowest_position);
			WaitingListReservation waitingListReservationToSave = waitingListReservationRepository.waitingListReservationById(id_current_WaitingListReservation_closest_to_zero);
			waitingListReservationToSave.setPosition_in_queue(positionInTheQueue);
			waitingListReservationRepository.save(waitingListReservationToSave);		
		} else if (listToCompare.size() == 1) {	
			WaitingListReservation waitingListReservationToSave = waitingListReservationRepository.waitingListReservationById(listToCompare.get(0).getId_waiting_list_reservation());
			waitingListReservationToSave.setPosition_in_queue(positionInTheQueue);
			waitingListReservationRepository.save(waitingListReservationToSave);	
			listToCompare.remove(0);
		}	
		return listToCompare; // return the list to compare again with the next position in queue
	}
	
	
	
	
	
	
	
	public int checkTheFirstInTheQueueForWaitingListForTheBook(List<WaitingListReservation> waitingListForTheBook) {
		int result = 0;
		A : for (int i = 0; i < waitingListForTheBook.size(); i++) {
			if (waitingListForTheBook.get(i).getPosition_in_queue() == 1) {
				result = i;
				break A;
			}
		}
		return result; // return the index of the reservation who is first in the queue
	}
	
	
	
	
	public boolean compareDateOfWaitingListReservation(Date date) {
		boolean result = false;		
		Calendar calendarOfArgumentDate = Calendar.getInstance(); 
		calendarOfArgumentDate.setTime(date);
		calendarOfArgumentDate.add(Calendar.HOUR_OF_DAY, 46);	
		Calendar calendarActualDateToday = Calendar.getInstance();
		calendarActualDateToday.setTime(new Date());	
		if (calendarOfArgumentDate.compareTo(calendarActualDateToday) > 0) {
			result = true;
		} 
		return result;
	}
	
	
}
