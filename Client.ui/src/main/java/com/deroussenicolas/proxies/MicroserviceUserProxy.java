package com.deroussenicolas.proxies;

import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deroussenicolas.beans.UserBean;

//For local testing on IDE you need that url below
//@FeignClient(name="bibliotheque-microservice", url = "localhost:8081")

//For deploying on tomcat you need that url below
@FeignClient(name="bibliotheque-microservice", url = "http://localhost:8081/Bibliotheque-microservice-0.0.1-SNAPSHOT")
public interface MicroserviceUserProxy {

	@GetMapping(value="/Users")
	@ResponseBody List<UserBean> listOfAllUser();
	
	@GetMapping(value = "/User/{id}")
	@ResponseBody UserBean oneUserWithId(@PathVariable int id);
	
	@GetMapping(value = "/User/{email}/{password}")
	@ResponseBody UserBean confirmUsername(@PathVariable String email, @PathVariable String password); 
	
	@GetMapping(value = "/loadUserByUsername/{email}")
	@ResponseBody UserBean loadUserByUsername(@PathVariable String email);
	
	@GetMapping(value="/UsersListToSendEmail")
	@ResponseBody List<UserBean> usersListToSendEmail();

	@PostMapping(value = "/User/validation")
	@ResponseBody UserBean test(@RequestBody String email);
}
