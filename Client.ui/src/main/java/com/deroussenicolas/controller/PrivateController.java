package com.deroussenicolas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.ReservationBean;
import com.deroussenicolas.beans.WaitingListReservationBean;
import com.deroussenicolas.proxies.MicroserviceReservationProxy;
import com.deroussenicolas.proxies.MicroserviceWaitingListReservationProxy;

@Controller
@SessionAttributes("userEmail")
public class PrivateController {
	

	@Autowired
	private MicroserviceReservationProxy microserviceReservationProxy;
	@Autowired
	private MicroserviceWaitingListReservationProxy microserviceWaitingListReservationProxy;
	
	@GetMapping("/private")
	public ModelAndView privateSpace(@SessionAttribute("userEmail") String userEmail) {
		ModelAndView modelView = new ModelAndView();
		if(userEmail == null) {
			modelView.setViewName("errors/access_denied");
			return modelView;
		}		
		List<WaitingListReservationBean> WaitingListReservationBeans = microserviceWaitingListReservationProxy.WaitingListReservationFromUser(userEmail);
		modelView.addObject("reservationWaitingListSizeList", WaitingListReservationBeans.size());			
		List<ReservationBean> reservationBeans = microserviceReservationProxy.reservationWithUserEmail(userEmail);
		int reservationSizeList = reservationBeans.size();
		modelView.addObject("reservationSizeList", reservationSizeList);
		modelView.addObject("reservationBeans", reservationBeans);
		modelView.setViewName("private/private");
		return modelView;	
	}
	

	



}
