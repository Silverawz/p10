package com.deroussenicolas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.ReservationBean;
import com.deroussenicolas.proxies.MicroserviceReservationProxy;

@Controller
@SessionAttributes("userEmail")
public class PrivateController {
	

	@Autowired
	private MicroserviceReservationProxy microserviceReservationProxy;

	
	@GetMapping("/private")
	public ModelAndView privateSpace(@SessionAttribute("userEmail") String userEmail) {
		ModelAndView modelView = new ModelAndView();
		if(userEmail == null) {
			modelView.setViewName("errors/access_denied");
			return modelView;
		}
		
		List<ReservationBean> reservationBeans = microserviceReservationProxy.reservationWithUserEmail(userEmail);
		int reservationSizeList = reservationBeans.size();
		modelView.addObject("reservationSizeList", reservationSizeList);
		modelView.addObject("reservationBeans", reservationBeans);
		modelView.setViewName("private/private");
		return modelView;	
	}
	

	



}
