package com.deroussenicolas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.proxies.MicroserviceWaitingListReservationProxy;

@Controller
@SessionAttributes("userEmail")
public class WaitingListReservationController {
	
	@Autowired
	private MicroserviceWaitingListReservationProxy microserviceWaitingListReservationProxy;
	
	
	@GetMapping("/reservation") 
	public ModelAndView waitingListReservationByBookId(@RequestParam(name="id_book") int id_book, @SessionAttribute("userEmail") String userEmail) {
		ModelAndView modelAndView = new ModelAndView();
		if(userEmail == null) {
			modelAndView.setViewName("errors/access_denied");
			return modelAndView;	
		}
		
		//try to insert the reservation , true =accepted, false=refused	
		boolean validation = microserviceWaitingListReservationProxy.WaitingListReservationCheck(id_book, userEmail); 
		System.out.println("confirmation="+validation);
		if(validation) {// if true = reservation valided
			modelAndView.setViewName("errors/access_denied");			
		} else { // else reservation refused
			modelAndView.setViewName("errors/access_denied");
		}		
		return modelAndView;	
	}

}
