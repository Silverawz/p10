package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.service.BookServiceImpl;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BookServiceUnitTest {
	
	
	private static BookServiceImpl bookServiceImpl = mock(BookServiceImpl.class);
	private static ReservationRepository reservationRepositoryMock = mock(ReservationRepository.class);
	private static BookRepository bookRepositoryMock = mock(BookRepository.class);
	private static List<Reservation> allReservationList;
	private static List<Book> allBookList;
	private static Book book = new Book();
	
    @BeforeClass
    public static void initBeforeClass() {
    	creatingTheBookList();
    	//bookServiceImpl = new BookServiceImpl();
    	//creatingTheReservationList();
    	given(bookServiceImpl.findAll()).willReturn(allBookList);
    	given(bookServiceImpl.findById(1)).willReturn(book);
    	//given(reservationMock.getCopy().getBook()).willReturn(new Book());
    	//given(bookServiceImpl.getAllBooksForEmail()).willReturn(allReservationList);
    }
    
    private static void creatingTheBookList() {
    	allBookList = new ArrayList<>();
    	allBookList.add(new Book()); 
    	allBookList.add(new Book()); 
    	allBookList.add(new Book());
    }
    
    @Test
    public void findAll() {
    	creatingTheBookList();
    	List<Book> bookList = bookServiceImpl.findAll();
    	assertEquals(3, bookList.size());
    } 
    
    @Test
    public void findById() {
    	assertEquals(bookServiceImpl.findById(1), book);
    }
    

}
