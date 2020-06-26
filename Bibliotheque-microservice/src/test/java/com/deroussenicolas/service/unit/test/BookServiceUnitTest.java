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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.BookServiceImpl;
import com.deroussenicolas.service.CopyService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BookServiceUnitTest {
	
	@InjectMocks
	private static BookServiceImpl bookServiceImpl;
	private static CopyRepository copyRepository = mock(CopyRepository.class);
	private static BookRepository bookRepository = mock(BookRepository.class);
	private static ReservationRepository reservationRepository = mock(ReservationRepository.class);
	private static WaitingListReservationRepository waitingListRepository = mock(WaitingListReservationRepository.class);
	private static CopyService copyService = mock(CopyService.class);
	private static List<Book> allBookList;
	private static Book book = new Book();
	
    @BeforeClass
    public static void initBeforeClass() {
    	bookServiceImpl = new BookServiceImpl();
    	creatingTheBookList();
    	given(bookRepository.findAll()).willReturn(allBookList);
    	given(bookRepository.findById(1)).willReturn(book);
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
    	assertEquals(bookServiceImpl.compareDateOfWaitingListReservation(date1), true);
    	
    	Date date2 = new Date();
    	Calendar calendar1 = Calendar.getInstance(); 
    	calendar1.set(1500, 1, 1);
    	date2 = calendar1.getTime();
    	assertEquals(bookServiceImpl.compareDateOfWaitingListReservation(date2), false);   	
    }
    
    @Test
    public void getAllBooksForEmail() {
    	bookServiceImpl.getAllBooksForEmail();
    }
    
    @Test
    public void queueSizeForEachBooks() {
    	bookServiceImpl.queueSizeForEachBooks();
    }
    
    @Test
    public void batchBook() {
    	bookServiceImpl.batchBook();
    }
    
    @Test
    public void checkTheFirstInTheQueueForWaitingListForTheBook() {
    	List<WaitingListReservation> waitingListForTheBook = new ArrayList<>();   	
    	WaitingListReservation waitingListReservation1 = new WaitingListReservation();
    	waitingListReservation1.setPosition_in_queue(1);
    	waitingListForTheBook.add(waitingListReservation1);
    	WaitingListReservation waitingListReservation2 = new WaitingListReservation();
    	waitingListReservation2.setPosition_in_queue(2);
    	waitingListForTheBook.add(waitingListReservation2);
    	assertEquals(bookServiceImpl.checkTheFirstInTheQueueForWaitingListForTheBook(waitingListForTheBook), 0);
    }

     
}
