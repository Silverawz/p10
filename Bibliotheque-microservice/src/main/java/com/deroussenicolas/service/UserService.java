package com.deroussenicolas.service;

import java.util.List;

import com.deroussenicolas.entities.User;

public interface UserService {

	List<User> getListUserToSendEmail();

	User findByEmail(String email);

	List<User> findAll();

	User findById(int id);

}
