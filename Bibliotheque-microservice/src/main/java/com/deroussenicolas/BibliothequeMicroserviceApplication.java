package com.deroussenicolas;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.dao.UserRepository;
import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.BookService;
import com.deroussenicolas.service.CopyService;
import com.deroussenicolas.service.ReservationService;
import com.deroussenicolas.service.UserService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BibliothequeMicroserviceApplication extends SpringBootServletInitializer implements CommandLineRunner {

	
	private WaitingListReservation waitingListReservation;
	@Autowired
	private WaitingListReservationRepository waitingListReservationRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookService BookService;
	@Autowired
	private CopyRepository copyRepository;
	@Autowired
	private CopyService copyService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ReservationRepository reservationRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BibliothequeMicroserviceApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BibliothequeMicroserviceApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		if (waitingListReservationRepository.findAll().size() < 3) {
		waitingListReservation = new WaitingListReservation();
		waitingListReservation.setBook(bookRepository.findAll().get(1));
		waitingListReservation.setUser(userRepository.findAll().get(1));
		waitingListReservation.setIs_archived(false);
		waitingListReservation.setIs_canceled(false);
		waitingListReservation.setPosition_in_queue(1);
		waitingListReservation.setDate_mail_send(new Date());
		waitingListReservationRepository.save(waitingListReservation);	
		
		WaitingListReservation waitingListReservation1 = new WaitingListReservation();
		waitingListReservation1.setBook(bookRepository.findAll().get(1));
		waitingListReservation1.setUser(userRepository.findAll().get(0));
		waitingListReservation1.setIs_archived(false);
		waitingListReservation1.setIs_canceled(false);
		waitingListReservation1.setPosition_in_queue(2);
		waitingListReservation1.setDate_mail_send(new Date());
		waitingListReservationRepository.save(waitingListReservation1);	
		}
		
		//System.out.println(copyService.numberOfCopiesNotAvailableForEachBook()); // OK FONCTIONNE BIEN
		//System.out.println(BookService.queueSizeForEachBooks()); // OK FONCTIONNE BIEN
		
		/*
		Reservation reservation = new Reservation();
		if(reservationRepository.findAll().size() < 6) {
	
		reservation.setUser(userRepository.findById(1));
		reservation.setCopy(copyRepository.findById(2));
		reservation.setIs_archived(false);
		reservation.setDate_begin("04.06.2020");
		reservation.setDate_end("25.06.2020");
		reservationRepository.save(reservation);
		
		} */
		
		//System.out.println(reservationService.lastRevervationForEachBooks());
		
		// System.err.println(userService.userOwnTheBookList(2));
		
		/*
		System.err.println("liste de date = "+reservationService.lastRevervationForEachBooks().get(3) +""+reservationService.lastRevervationForEachBooks().get(5));
		System.err.println("-----------------");
		System.err.println("liste de date = "+reservationService.lastRevervationForEachBooks() +"\n\n");
		System.err.println("taille de la liste de date = "+reservationService.lastRevervationForEachBooks().size());
		*/
		
		
	}

}
