package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.dao.UserRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.entities.User;
import com.deroussenicolas.service.UserServiceImpl;
import com.deroussenicolas.service.WaitingListService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

	@InjectMocks
	private static UserServiceImpl userServiceImpl;
	private static UserRepository userRepository = mock(UserRepository.class);
	private static ReservationRepository reservationRepository = mock(ReservationRepository.class);
	private static BookRepository bookRepository = mock(BookRepository.class);
	private static WaitingListService waitingListService = mock(WaitingListService.class);
	private static List<User> userList;
	private static List<Reservation> reservationList;
	private static List<Book> bookList;
	private static User user = new User();
	
    @BeforeClass
    public static void initBeforeClass() {
    	creatingTheUserListAndReservationListAndBookList();
    	given(userRepository.findByEmail("userEmail")).willReturn(user);
    	given(userRepository.findAll()).willReturn(userList);
    	given(userRepository.findById(1)).willReturn(user);
    	given(reservationRepository.reservationListNotArchived(false)).willReturn(reservationList);
    	given(bookRepository.findAll()).willReturn(bookList);
    }
    
    private static void creatingTheUserListAndReservationListAndBookList() {
    	userList = new ArrayList<>();
    	userList.add(new User());
    	userList.add(new User());
    	userList.add(new User());
    	
    	reservationList = new ArrayList<>();   	
    	Reservation reservation1 = new Reservation();
    	reservation1.setDate_end("10.10.1500");
    	reservationList.add(reservation1);
    	
    	Reservation reservation2 = new Reservation();
    	reservation2.setDate_end("10.10.2500");    	
    	reservationList.add(reservation2);  
    	
    	bookList = new ArrayList<>(); 
    	Book book1 = new Book();
    	book1.setId_book(1);
    	bookList.add(book1);
    	
    	Book book2 = new Book();
    	book2.setId_book(2);
    	bookList.add(book2);
    	
    	Book book3 = new Book();
    	book3.setId_book(3);  	
    	bookList.add(book3);
    }
    
    @Test
    public void getListUserToSendEmail() {
    	List<User> userList = userServiceImpl.getListUserToSendEmail();
    	assertEquals(userList.size(), 1);
    }
    
    @Test
    public void findByEmail() {
    	assertEquals(userServiceImpl.findByEmail("userEmail"), user);
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
    
    @Test
    public void userOwnTheBookList() {
    	List<Boolean> resultBooleanList =  userServiceImpl.userOwnTheBookList(1);
    	assertEquals(resultBooleanList.size(), 0);
    }
}
