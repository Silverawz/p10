package com.deroussenicolas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.UserBean;
import com.deroussenicolas.proxies.MicroserviceUserProxy;

@Controller
@SessionAttributes("userEmail")
public class HomeController {

	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	
	@GetMapping("/login")
	public ModelAndView login(ModelAndView modelAndView) {
		ModelAndView modelView = new ModelAndView();	
		modelView.setViewName("home/login");
		return modelView;	
	}
	
	@RequestMapping(value={"/home","/"}, method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserBean userBean = microserviceUserProxy.loadUserByUsername(auth.getName());
		modelView.addObject("userEmail", userBean.getEmail());
		modelView.setViewName("home/home");
		return modelView;
	}
	

	
}
