package com.deroussenicolas;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.UserRepository;
import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.WaitingListReservation;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BibliothequeMicroserviceApplication extends SpringBootServletInitializer implements CommandLineRunner {

	
	private WaitingListReservation waitingListReservation;
	@Autowired
	private WaitingListReservationRepository waitingListReservationRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BibliothequeMicroserviceApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BibliothequeMicroserviceApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		if(waitingListReservationRepository.findAll().size() < 2) {
		waitingListReservation = new WaitingListReservation();
		waitingListReservation.setBook(bookRepository.findAll().get(1));
		waitingListReservation.setUser(userRepository.findAll().get(1));
		waitingListReservation.setIs_archived(false);
		waitingListReservation.setIs_canceled(false);
		waitingListReservation.setPosition_in_queue(2);
		waitingListReservationRepository.save(waitingListReservation);	
		}
	}

}
