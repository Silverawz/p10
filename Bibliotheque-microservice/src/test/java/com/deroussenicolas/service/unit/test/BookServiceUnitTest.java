package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Book;
import com.deroussenicolas.service.BookServiceImpl;

@SpringBootTest
public class BookServiceUnitTest {
	
	
	private static BookServiceImpl bookServiceImpl = mock(BookServiceImpl.class);
	private static BookServiceImpl bookServiceImplNotMock;
	private static List<Book> allBookList;
	private static Book book = new Book();
	
    @BeforeClass
    public static void initBeforeClass() {
    	bookServiceImplNotMock = new BookServiceImpl();
    	creatingTheBookList();
    	given(bookServiceImpl.findAll()).willReturn(allBookList);
    	given(bookServiceImpl.findById(1)).willReturn(book);
    }
    
    private static void creatingTheBookList() {
    	allBookList = new ArrayList<>();
    	allBookList.add(new Book()); 
    	allBookList.add(new Book()); 
    	allBookList.add(new Book());
    }
     
    @Test
    public void findAll() {  	
    	List<Book> bookList = bookServiceImpl.findAll();
    	assertEquals(3, bookList.size());
    } 
    
    @Test
    public void findById() {
    	assertEquals(bookServiceImpl.findById(1), book);
    }
    
    @Test
    public void compareDateOfWaitingListReservation() {
    	Date date1 = new Date();
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.set(2500, 1, 1);
    	date1 = calendar.getTime();
    	assertEquals(bookServiceImplNotMock.compareDateOfWaitingListReservation(date1), true);
    	
    	Date date2 = new Date();
    	Calendar calendar1 = Calendar.getInstance(); 
    	calendar1.set(1500, 1, 1);
    	date2 = calendar1.getTime();
    	assertEquals(bookServiceImplNotMock.compareDateOfWaitingListReservation(date2), false);   	
    }
     
}
