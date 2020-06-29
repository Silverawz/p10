package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.deroussenicolas.entities.User;

@SpringBootTest
public class UserUnitTest {
	
	private static User user;
	private static String user_firstname = "Jean";
	private static String user_lastname = "Pierre";
	private static String user_email = "Jean@aol.fr";
	private static String user_password = "1234";
	private static boolean user_active = true;
	private static BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @BeforeClass
    public static void initBeforeClass() {
    	bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	user_password = bCryptPasswordEncoder.encode(user_password);
        user = new User(user_firstname, user_lastname, user_email, user_password, user_active);
    }
    
    @Test
    public void constructorWithDefaultValuesThenModification() {
    	assertEquals(user.getFirstname(), user_firstname);
    	assertEquals(user.getLastname(), user_lastname);
    	assertEquals(user.getPassword(), user_password);
    	user.setActive(false);
    	String newPassword = bCryptPasswordEncoder.encode("9999");
    	user.setPassword(newPassword);
    	user.setFirstname("newName");
    	user.setLastname("newLastName");
    	user.setEmail("aaaaaaaaaaaa@gmail.com");
    	assertEquals(user.getFirstname(), "newName");
    	assertEquals(user.getLastname(), "newLastName");
    	assertEquals(user.getPassword(), newPassword);
    	assertEquals(user.isActive(), false);
    	assertEquals(user.getEmail(), "aaaaaaaaaaaa@gmail.com");	
    }
    
}
