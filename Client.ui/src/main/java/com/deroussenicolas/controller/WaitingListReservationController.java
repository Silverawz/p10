package com.deroussenicolas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.BookBean;
import com.deroussenicolas.beans.WaitingListReservationBean;
import com.deroussenicolas.proxies.MicroserviceBookProxy;
import com.deroussenicolas.proxies.MicroserviceWaitingListReservationProxy;

@Controller
@SessionAttributes("userEmail")
public class WaitingListReservationController {
	
	@Autowired
	private MicroserviceWaitingListReservationProxy microserviceWaitingListReservationProxy;
	@Autowired
	private MicroserviceBookProxy microserviceBookProxy;
	
	@GetMapping("/reservation") 
	public ModelAndView waitingListReservationByBookId(@RequestParam(name="id_book") int id_book, @SessionAttribute("userEmail") String userEmail) {
		ModelAndView modelAndView = new ModelAndView();
		if(userEmail == null) {
			modelAndView.setViewName("errors/access_denied");
			return modelAndView;	
		}		
		//try to insert the reservation , true =accepted, false=refused	
		boolean validation = microserviceWaitingListReservationProxy.WaitingListReservationCheck(id_book, userEmail); 
		if(validation) {// if true = reservation valided
			modelAndView.addObject("book", microserviceBookProxy.oneBookWithId(id_book));
			modelAndView.setViewName("private/waitingListReservationApproved");			
		} else { // else reservation refused
			modelAndView.setViewName("errors/access_denied");
		}		
		return modelAndView;	
	}

	@GetMapping("/showAllReservationWaitingList")
	public ModelAndView showAllReservationWaitingList(@SessionAttribute("userEmail") String userEmail) {
		ModelAndView modelAndView = new ModelAndView();
		if(userEmail == null) {
			modelAndView.setViewName("errors/access_denied");
			return modelAndView;
		}
		List<WaitingListReservationBean> waitingListReservationOfUser = microserviceWaitingListReservationProxy.WaitingListReservationFromUser(userEmail);
		for (WaitingListReservationBean waitingListReservationBean : waitingListReservationOfUser) {
			BookBean bookBean = microserviceBookProxy.waitingListReservationGettingBook(waitingListReservationBean.getId_waiting_list_reservation());
			waitingListReservationBean.setBook(bookBean);
		}
		modelAndView.addObject("waitingListReservationOfUser", waitingListReservationOfUser);
		modelAndView.setViewName("private/waitingListReservation");		
		return modelAndView;
	}
	
	@GetMapping("/cancelReservationWaitingList")
	public ModelAndView showAllReservationWaitingList(@SessionAttribute("userEmail") String userEmail, 
			@RequestParam(name="id_waiting_list_reservation") int id_waiting_list_reservation) {
		ModelAndView modelAndView = new ModelAndView();
		if(userEmail == null) {
			modelAndView.setViewName("errors/access_denied");
			return modelAndView;
		}
		boolean result = microserviceWaitingListReservationProxy.WaitingListReservationCancel(id_waiting_list_reservation, userEmail);
		if(result) {	
			BookBean bookBean = microserviceBookProxy.waitingListReservationGettingBook(id_waiting_list_reservation);
			int id_book = bookBean.getId_book();
			modelAndView.addObject("book", microserviceBookProxy.oneBookWithId(id_book));
			modelAndView.setViewName("private/waitingListReservationCanceled");	
		} else {
			modelAndView.setViewName("errors/access_denied");
		}
		return modelAndView;	
	}
}
