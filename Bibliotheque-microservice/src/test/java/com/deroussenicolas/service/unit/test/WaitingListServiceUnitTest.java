package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.BookService;
import com.deroussenicolas.service.WaitingListReservationServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WaitingListServiceUnitTest {

	@InjectMocks
	private static WaitingListReservationServiceImpl waitingListReservationServiceImpl;
	private static WaitingListReservation waitingListReservation;
	private static List<WaitingListReservation> waitingListReservationList;
	private static List<Book> bookList;
	private static WaitingListReservationRepository waitingListRepository = mock(WaitingListReservationRepository.class);
	private static BookService bookService = mock(BookService.class);
	
    @BeforeClass
    public static void initBeforeClass() {
    	waitingListReservationServiceImpl = new WaitingListReservationServiceImpl();
    	creatingTheWaitingListReservationListAndTheBookList();
    	given(waitingListRepository.findAll()).willReturn(waitingListReservationList);
    	given(waitingListRepository.waitingListReservationOfBookWithParams(1, false, false)).willReturn(waitingListReservationList);
    	given(waitingListRepository.waitingListReservationOfUserBookWithParamsForASingleObject(1, 1, false, false)).willReturn(waitingListReservation);  	
    	given(waitingListRepository.waitingListReservationById(1)).willReturn(waitingListReservation);
    	given(waitingListRepository.waitingListReservationOfUserWithParamsArchived(1, false)).willReturn(waitingListReservationList);      	  	
    	given(waitingListRepository.waitingListReservationOfUserWithParams(1, false, false)).willReturn(waitingListReservationList);   
    	given(bookService.findAll()).willReturn(bookList);   
    }
    
    private static void creatingTheWaitingListReservationListAndTheBookList() {
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
    	
    	
    	waitingListReservationList = new ArrayList<>();
    	WaitingListReservation waitingListReservation1 = new WaitingListReservation();
    	waitingListReservation1.setBook(book1);
    	waitingListReservationList.add(waitingListReservation1);
    	
    	WaitingListReservation waitingListReservation2 = new WaitingListReservation();
    	waitingListReservation2.setBook(book2);    	    	
    	waitingListReservationList.add(waitingListReservation2);
    	
    	WaitingListReservation waitingListReservation3 = new WaitingListReservation();
    	waitingListReservation3.setBook(book3);   	  	
    	waitingListReservationList.add(waitingListReservation3);
    }
    
    @Test
    public void findAll() {  	
    	assertEquals(mockingDetails(waitingListRepository).isMock(), true); 
    	List<WaitingListReservation> waitingListReservationList = waitingListReservationServiceImpl.findAll();
    	assertEquals(3, waitingListReservationList.size()); 
    }  
    
    @Test
    public void waitingListReservationOfBookWithParams() {  	
    	List<WaitingListReservation> waitingListReservationList = waitingListReservationServiceImpl.waitingListReservationOfBookWithParams(1, false, false);
    	assertEquals(3, waitingListReservationList.size());
    }
    
    @Test
    public void waitingListReservationOfUserBookWithParamsForASingleObject() {
    	WaitingListReservation waitingListReservationTest = waitingListReservationServiceImpl.waitingListReservationOfUserBookWithParamsForASingleObject(1, 1, false, false);
    	assertEquals(waitingListReservationTest, waitingListReservation);
    }
    
    @Test
    public void waitingListReservationById() {
    	WaitingListReservation waitingListReservationTest = waitingListReservationServiceImpl.waitingListReservationById(1);
    	assertEquals(waitingListReservationTest, waitingListReservation);
    }
    
    @Test
    public void waitingListReservationOfUserWithParamsArchived() {
    	List<WaitingListReservation> waitingListReservationList = waitingListReservationServiceImpl.waitingListReservationOfUserWithParamsArchived(1, false);
    	assertEquals(3, waitingListReservationList.size());
    }
    
    @Test 
    public void checkIfUserHasMadeAReservationForEchBooks() {
    	List<Boolean> booleanListParameters = new ArrayList<>();
    	booleanListParameters.add(false);
    	booleanListParameters.add(false);
    	booleanListParameters.add(false); 	
    	List<Boolean> booleanListResult = waitingListReservationServiceImpl.checkIfUserHasMadeAReservationForEchBooks(booleanListParameters, 1);
    	assertEquals(booleanListResult.get(0), true);
    	assertEquals(booleanListResult.get(1), true);
    	assertEquals(booleanListResult.get(2), true);
    }
    
}
