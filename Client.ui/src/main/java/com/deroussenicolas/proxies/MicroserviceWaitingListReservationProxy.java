package com.deroussenicolas.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deroussenicolas.beans.WaitingListReservationBean;

//For local testing on IDE you need that url below
@FeignClient(name="bibliotheque-microservice", url = "localhost:8081")

//For deploying on tomcat you need that url below
//@FeignClient(name="bibliotheque-microservice", url = "http://localhost:8081/Bibliotheque-microservice-0.0.1-SNAPSHOT")
public interface MicroserviceWaitingListReservationProxy {

	@GetMapping(value="/WaitingList")
	@ResponseBody List<WaitingListReservationBean> waitingList();
	
	@GetMapping(value="/WaitingListReservationCheck/{id_book}/{userEmail}")
	@ResponseBody boolean WaitingListReservationCheck(@PathVariable int id_book, @PathVariable String userEmail);
}
