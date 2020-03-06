package com.deroussenicolas.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.deroussenicolas.entities.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
	
	@Query("from User where id_user=?1")
	User findById(int id);
	
	@Query("from User where email=?1")
	User findByEmail(String email);
}