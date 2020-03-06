package com.deroussenicolas.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Reservation;

@Service("BookServiceImpl")
public class BookServiceImpl implements BookService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private BookRepository bookRepository;
	
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

}
