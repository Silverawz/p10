package com.deroussenicolas.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deroussenicolas.entities.User;
import com.deroussenicolas.service.UserService;


@RestController
public class UserController {

	@Autowired
	private UserService userservice;
	
	@GetMapping(value="/Users")
    public @ResponseBody List<User> listOfAllUser() {
		List<User> listUser = userservice.findAll();
        return listUser;
    }
	
    @GetMapping(value = "/User/{id}")
    public User oneUserWithId(@PathVariable int id) {
    	return userservice.findById(id);
    }
    
    @GetMapping(value = "/User/{email}/{password}")
    public User confirmUsername(@PathVariable String email, @PathVariable String password) {
    	User user = userservice.findByEmail(email);
    	if(user != null && user.getPassword().equals(password)) {
    		return user;		
    	}  	
    	return null;
    }
    
    //
    @PostMapping(value = "/User/validation")
    public User test(@RequestBody String email) {
    	User user = userservice.findByEmail(email);
    	return user;
    }  
    //
    
    @GetMapping(value = "/loadUserByUsername/{email}")
    public User loadUserByUsername(@PathVariable String email) {
    	User user = userservice.findByEmail(email);
    	if(user != null) {
    		return user;
    	}
    	return null;
    }
    
	@GetMapping(value="/UsersListToSendEmail")
    public @ResponseBody List<User> usersListToSendEmail() {
		List<User> usersListToSendEmail = userservice.getListUserToSendEmail();
        return usersListToSendEmail;
    }
	
	@GetMapping(value="/booksOwnedByUser/{user_id}")
	public @ResponseBody List<Boolean> booksOwnedByUserInOrderAsBoolean(@PathVariable int user_id) {
		return userservice.userOwnTheBookList(user_id);
	}
}
