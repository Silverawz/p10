package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.User;
import com.deroussenicolas.service.UserServiceImpl;

@SpringBootTest
public class UserServiceUnitTest {

	private static UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
	private static List<User> userList;
	private static User user = new User();
	
    @BeforeClass
    public static void initBeforeClass() {
    	creatingTheUserList();
    	given(userServiceImpl.findAll()).willReturn(userList);
    	given(userServiceImpl.findById(1)).willReturn(user);
    }
    
    private static void creatingTheUserList() {
    	userList = new ArrayList<>();
    	userList.add(new User());
    	userList.add(new User());
    	userList.add(new User());
    }
    
    @Test
    public void findAll() {  	
    	List<User> userList = userServiceImpl.findAll();
    	assertEquals(3, userList.size());
    } 
    
    @Test
    public void findById() {
    	assertEquals(userServiceImpl.findById(1), user);
    }  
    
}
