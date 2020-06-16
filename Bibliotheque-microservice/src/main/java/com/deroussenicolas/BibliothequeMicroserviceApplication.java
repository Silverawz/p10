package com.deroussenicolas;


import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.deroussen.batch.MyTask;
import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.dao.UserRepository;
import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.entities.User;
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
		

			/*

			

			


			

		*/
		
		
		Book book = new Book();
		book.setBook_author("Richard");
		book.setBook_editor("Pluton");
		book.setBook_name("La jungle éclairée");
		bookRepository.save(book);
		
		Copy copy = new Copy();
		copy.setBook(book);
		copy.setStatus('0');
		copyRepository.save(copy);
		
		Copy copy1 = new Copy();
		copy1.setBook(book);
		copy1.setStatus('0');
		copyRepository.save(copy1);
		
		Book book1 = new Book();
		book1.setBook_author("Nicolas");
		book1.setBook_editor("Neptune");
		book1.setBook_name("De rien!");
		bookRepository.save(book1);
		
		Copy copy2 = new Copy();
		copy2.setBook(book1);
		copy2.setStatus('0');
		copyRepository.save(copy2);
		
		Copy copy3 = new Copy();
		copy3.setBook(book1);
		copy3.setStatus('0');
		copyRepository.save(copy3);
		
		Book book2 = new Book();
		book2.setBook_author("Cyril");
		book2.setBook_editor("Terre");
		book2.setBook_name("Un jour peut être");
		bookRepository.save(book2);
		
		Copy copy4 = new Copy();
		copy4.setBook(book2);
		copy4.setStatus('0');
		copyRepository.save(copy4);
		
		Copy copy5 = new Copy();
		copy5.setBook(book2);
		copy5.setStatus('0');
		copyRepository.save(copy5);
		
		Book book3 = new Book();
		book3.setBook_author("Jack");
		book3.setBook_editor("Lune");
		book3.setBook_name("La ressource manquante");
		bookRepository.save(book3);
		
		Copy copy6 = new Copy();
		copy6.setBook(book3);
		copy6.setStatus('0');
		copyRepository.save(copy6);
		
		Book book4 = new Book();
		book4.setBook_author("Paul");
		book4.setBook_editor("Fleur");
		book4.setBook_name("La fille de l'est");
		bookRepository.save(book4);		
		
		Copy copy7 = new Copy();
		copy7.setBook(book4);
		copy7.setStatus('0');
		copyRepository.save(copy7);
		
		
			
			User user3 = new User();
			user3.setActive(true);
			user3.setEmail("aaa@aol.fr");
			user3.setFirstname("John");
			user3.setLastname("Rick");
			user3.setPassword(new BCryptPasswordEncoder().encode("123456"));
			userRepository.save(user3);
			
			User user4 = new User();
			user4.setActive(true);
			user4.setEmail("bbb@aol.fr");
			user4.setFirstname("Hely");
			user4.setLastname("Kyc");
			user4.setPassword(new BCryptPasswordEncoder().encode("123456"));
			userRepository.save(user4);
			
			User user = new User();
			user.setActive(true);
			user.setEmail("ccc@aol.fr");
			user.setFirstname("Rick");
			user.setLastname("Eyo");
			user.setPassword(new BCryptPasswordEncoder().encode("123456"));
			userRepository.save(user);
			
			User user1 = new User();
			user1.setActive(true);
			user1.setEmail("ddd@aol.fr");
			user1.setFirstname("Morty");
			user1.setLastname("Rim");
			user1.setPassword(new BCryptPasswordEncoder().encode("123456"));
			userRepository.save(user1);
			
			User user2 = new User();
			user2.setActive(true);
			user2.setEmail("eee@aol.fr");
			user2.setFirstname("Hector");
			user2.setLastname("Koi");
			user2.setPassword(new BCryptPasswordEncoder().encode("123456"));
			userRepository.save(user2);
		
		/*
		Reservation reservation = new Reservation();
		reservation.setCopy(copy);
		reservation.setDate_begin("07.06.2020");
		reservation.setDate_end("07.08.2020");
		reservation.setIs_archived(false);
		reservation.setUser(user);
		copy.setStatus('1');
		copyRepository.save(copy);
		reservationRepository.save(reservation);
		
		Reservation reservation1 = new Reservation();
		reservation1.setCopy(copy1);
		reservation1.setDate_begin("06.06.2020");
		reservation1.setDate_end("13.08.2020");
		reservation1.setIs_archived(false);
		reservation1.setUser(user1);
		copy1.setStatus('1');
		copyRepository.save(copy1);
		reservationRepository.save(reservation1);

		Reservation reservation2 = new Reservation();
		reservation2.setCopy(copy7);
		reservation2.setDate_begin("06.06.2020");
		reservation2.setDate_end("13.08.2020");
		reservation2.setIs_archived(false);
		reservation2.setUser(user2);
		copy1.setStatus('1');
		copyRepository.save(copy7);
		reservationRepository.save(reservation2);
		
		Reservation reservation3 = new Reservation();
		reservation3.setCopy(copy6);
		reservation3.setDate_begin("06.06.2020");
		reservation3.setDate_end("13.08.2020");
		reservation3.setIs_archived(false);
		reservation3.setUser(user2);
		copy1.setStatus('1');
		copyRepository.save(copy6);
		reservationRepository.save(reservation3);
		
		Reservation reservation4 = new Reservation();
		reservation4.setCopy(copy5);
		reservation4.setDate_begin("06.06.2020");
		reservation4.setDate_end("13.08.2020");
		reservation4.setIs_archived(false);
		reservation4.setUser(user2);
		copy1.setStatus('1');
		copyRepository.save(copy5);
		reservationRepository.save(reservation4);
		*/
		
		/*
		Reservation reservation40 = new Reservation();
		Copy copyId11 = copyRepository.findById(11);
		reservation40.setCopy(copyId11);
		reservation40.setDate_begin("06.06.2020");
		reservation40.setDate_end("13.08.2020");
		reservation40.setIs_archived(false);
		reservation40.setUser(user2);
		copyId11.setStatus('1');
		copyRepository.save(copyId11);
		reservationRepository.save(reservation40);
		*/
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(2020, 5, 13, 14, 58);
		Date date = calendar.getTime();
		
		/*
		waitingListReservation = new WaitingListReservation();
		waitingListReservation.setBook(bookRepository.findAll().get(0));
		waitingListReservation.setUser(userRepository.findAll().get(0));
		waitingListReservation.setIs_archived(false);
		waitingListReservation.setIs_canceled(false);
		waitingListReservation.setPosition_in_queue(1);
		waitingListReservation.setDate_mail_send(date);
		waitingListReservationRepository.save(waitingListReservation);	
		
		WaitingListReservation waitingListReservation1 = new WaitingListReservation();
		waitingListReservation1.setBook(bookRepository.findAll().get(0));
		waitingListReservation1.setUser(userRepository.findAll().get(1));
		waitingListReservation1.setIs_archived(false);
		waitingListReservation1.setIs_canceled(false);
		waitingListReservation1.setPosition_in_queue(1);
		waitingListReservationRepository.save(waitingListReservation1);			
		
		WaitingListReservation waitingListReservation2 = new WaitingListReservation();
		waitingListReservation2.setBook(bookRepository.findAll().get(0));
		waitingListReservation2.setUser(userRepository.findAll().get(2));
		waitingListReservation2.setIs_archived(false);
		waitingListReservation2.setIs_canceled(false);
		waitingListReservation2.setPosition_in_queue(3);
		waitingListReservationRepository.save(waitingListReservation2);
		
		WaitingListReservation waitingListReservation3 = new WaitingListReservation();
		waitingListReservation3.setBook(bookRepository.findAll().get(0));
		waitingListReservation3.setUser(userRepository.findAll().get(3));
		waitingListReservation3.setIs_archived(false);
		waitingListReservation3.setIs_canceled(false);
		waitingListReservation3.setPosition_in_queue(2);
		waitingListReservationRepository.save(waitingListReservation3);	
		*/
		
		for (int i = 0; i < 8 ; i ++) {
			WaitingListReservation waitingListReservationloop = new WaitingListReservation();
			waitingListReservationloop.setBook(bookRepository.findAll().get(1));
			waitingListReservationloop.setUser(userRepository.findAll().get(3));
			waitingListReservationloop.setIs_archived(false);
			waitingListReservationloop.setIs_canceled(false);
			waitingListReservationloop.setPosition_in_queue(i+1);
			
			
			if(i == 0 || i == 1 || i == 2) {
				Calendar calendarr = Calendar.getInstance(); 
				calendarr.set(2019, 3, i);
				Date dateee = calendarr.getTime();
				waitingListReservationloop.setDate_mail_send(dateee);
			} 
			waitingListReservationRepository.save(waitingListReservationloop);	
		}
		
		for (int i = 0; i < 8 ; i ++) {
			WaitingListReservation waitingListReservationloop = new WaitingListReservation();
			waitingListReservationloop.setBook(bookRepository.findAll().get(2));
			waitingListReservationloop.setUser(userRepository.findAll().get(3));
			waitingListReservationloop.setIs_archived(false);
			waitingListReservationloop.setIs_canceled(false);
			waitingListReservationloop.setPosition_in_queue(i+1);
			
			if(i == 0 || i == 1 || i == 2) {
				Calendar calendarr = Calendar.getInstance(); 
				calendarr.set(2019, 3, i);
				Date dateee = calendarr.getTime();
				waitingListReservationloop.setDate_mail_send(dateee);
			} 
			waitingListReservationRepository.save(waitingListReservationloop);	
		}
		
		for (int i = 0; i < 8 ; i ++) {
			WaitingListReservation waitingListReservationloop = new WaitingListReservation();
			waitingListReservationloop.setBook(bookRepository.findAll().get(3));
			waitingListReservationloop.setUser(userRepository.findAll().get(3));
			waitingListReservationloop.setIs_archived(false);
			waitingListReservationloop.setIs_canceled(false);
			waitingListReservationloop.setPosition_in_queue(i+1);
			
			if(i == 0 || i == 1 || i == 2) {
				Calendar calendarr = Calendar.getInstance(); 
				calendarr.set(2019, 3, i);
				Date dateee = calendarr.getTime();
				waitingListReservationloop.setDate_mail_send(dateee);
			} 
			waitingListReservationRepository.save(waitingListReservationloop);	
		}
		
		
		System.out.println("result="+BookService.batchBook().size());
		
		
		//System.out.println(userService.userOwnTheBookList(18));
		/*
		for (Reservation reservation : reservationRepository.findAll()) {
			reservationRepository.delete(reservation);
		}
		
		for (Copy copy : copyRepository.findAll()) {
			copy.setStatus('0');
			copyRepository.save(copy);
		}*/
		/*
		if (waitingListReservationRepository.findAll().size() < 2) {
		waitingListReservation = new WaitingListReservation();
		waitingListReservation.setBook(bookRepository.findAll().get(1));
		waitingListReservation.setUser(userRepository.findAll().get(1));
		waitingListReservation.setIs_archived(false);
		waitingListReservation.setIs_canceled(false);
		waitingListReservation.setPosition_in_queue(1);
		waitingListReservation.setDate_mail_send(new Date());
		waitingListReservationRepository.save(waitingListReservation);	
		
		WaitingListReservation waitingListReservation1 = new WaitingListReservation();
		waitingListReservation1.setBook(bookRepository.findAll().get(0));
		waitingListReservation1.setUser(userRepository.findAll().get(0));
		waitingListReservation1.setIs_archived(false);
		waitingListReservation1.setIs_canceled(false);
		waitingListReservation1.setPosition_in_queue(1);
		waitingListReservation1.setDate_mail_send(new Date());
		waitingListReservationRepository.save(waitingListReservation1);	
		}*/
		
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
		
		/*
		 * Set the time for the batch, here is 2 AM
		 */
		/*
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		*/
		/*
		 * Email batch
		 * Sending a mail to every user who didnt return back their book according to the reservation date
		 * Every night at 2 AM, the task will run
		 */
		/*
		Timer timer = new Timer(); 
		timer.schedule(
				new MyTask(smtpMailSender,microserviceUserProxy, microserviceBookProxy), 
				today.getTime(), 
				TimeUnit.MILLISECONDS.convert(1,TimeUnit.HOURS)
		); */
		// period: 1 day DAYS 
		
	}

}
