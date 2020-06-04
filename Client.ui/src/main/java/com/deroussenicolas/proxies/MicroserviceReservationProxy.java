package com.deroussenicolas.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deroussenicolas.beans.ReservationBean;

//For local testing on IDE you need that url below
@FeignClient(name="bibliotheque-microservice", url = "localhost:8081")

//For deploying on tomcat you need that url below
//@FeignClient(name="bibliotheque-microservice", url = "http://localhost:8081/Bibliotheque-microservice-0.0.1-SNAPSHOT")
public interface MicroserviceReservationProxy {

	@GetMapping(value="/Reservations")
	@ResponseBody List<ReservationBean> listOfAllReservation();
	
	@GetMapping(value = "/Reservation/{id}")
	@ResponseBody ReservationBean oneReservationWithId(@PathVariable int id);
	
	@GetMapping(value = "/ReservationUser/{id}")
	@ResponseBody List<ReservationBean> reservationWithUserId(@PathVariable int id);
	
	@GetMapping(value = "/ExtendReservation/{id}")
	@ResponseBody ReservationBean extendReservationWithId(@PathVariable int id); 
	
	@GetMapping(value = "/ReservationUserWithEmail/{email}")
	@ResponseBody List<ReservationBean> reservationWithUserEmail(@PathVariable String email);
	
	@GetMapping(value="/ReservationsNotArchived")
	@ResponseBody List<ReservationBean> listOfAllReservationNotArchived();
	
	@GetMapping(value = "/ConfirmReservation/{id}/{email}")
	@ResponseBody Boolean confirmReservationWithReservationIdAndUserEmail(@PathVariable int id, @PathVariable String email);
	
}
