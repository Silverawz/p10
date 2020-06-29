package com.deroussenicolas;


import java.util.Calendar;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.deroussen.batch.MyTask;
import com.deroussenicolas.service.BookService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BibliothequeMicroserviceApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private BookService BookService;
	
	public static void main(String[] args) {
		SpringApplication.run(BibliothequeMicroserviceApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BibliothequeMicroserviceApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		Calendar today = Calendar.getInstance();
		Timer timer = new Timer(); 
		timer.schedule(new MyTask(BookService), today.getTime(), 1000*60*60); // every hours
		
	}

}
