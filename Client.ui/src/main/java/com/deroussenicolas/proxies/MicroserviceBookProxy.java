package com.deroussenicolas.proxies;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deroussenicolas.beans.BookBean;

//For local testing on IDE you need that url below
@FeignClient(name="bibliotheque-microservice", url = "localhost:8081")

//For deploying on tomcat you need that url below
//@FeignClient(name="bibliotheque-microservice", url = "http://localhost:8081/Bibliotheque-microservice-0.0.1-SNAPSHOT")
public interface MicroserviceBookProxy {
	
	
	@GetMapping(value="/Books")
	@ResponseBody List<BookBean> listOfAllBooks();
    
	@GetMapping(value = "/Book/{id}")
	@ResponseBody BookBean oneBookWithId(@PathVariable("id") int id);
		
	@GetMapping(value = "/BooksOfUser/{email}")
	@ResponseBody List<BookBean> allBookWithUserEmail(@PathVariable String email);
	
	@GetMapping(value="/BooksToEmail")
	@ResponseBody List<BookBean> listOfAllBooksToSendEmail();
	
    @GetMapping(value = "/Book/LastReservationForEachBooks")
    @ResponseBody List<Date> lastRevervationForEachBooks();
    
    @GetMapping(value = "/Book/QueueSizeForEachsBooks")
    @ResponseBody List<Integer> queueSizeForEachsBooks();
    
    @GetMapping(value = "/WaitingListReservationGettingBook/{id}")
    @ResponseBody BookBean waitingListReservationGettingBook(@PathVariable("id") int id);
}
