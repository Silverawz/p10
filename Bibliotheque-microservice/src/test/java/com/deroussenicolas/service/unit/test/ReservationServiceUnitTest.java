package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.ReservationRepository;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.service.ReservationServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceUnitTest {

	@InjectMocks
	private static ReservationServiceImpl reservationServiceImpl; // mock(ReservationServiceImpl.class)
	private static ReservationRepository reservationRepository = mock(ReservationRepository.class);
	private static BookRepository bookRepository = mock(BookRepository.class);
	private static List<Reservation> reservationList;
	private static Reservation reservation = new Reservation();
	
    @BeforeClass
    public static void initBeforeClass() {
    	reservationServiceImpl = new ReservationServiceImpl();
    	creatingTheReservationList();
    	given(reservationRepository.findAll()).willReturn(reservationList);
    	given(reservationRepository.findById(1)).willReturn(reservation);
    	given(reservationRepository.reservationListOfUser(1)).willReturn(reservationList);
    	given(reservationRepository.reservationListNotArchived(false)).willReturn(reservationList);
    }
    
    private static void creatingTheReservationList() {
    	reservationList = new ArrayList<>();
    	reservationList.add(new Reservation());
    	reservationList.add(new Reservation());
    	reservationList.add(new Reservation());
    	
    	reservation.setDate_end("10.10.1950");
    }
    
    @Test
    public void reservationListOfUser() {
    	assertEquals(reservationServiceImpl.reservationListOfUser(1).size(), 3);
    }
    
    @Test
    public void findAll() {  	
    	List<Reservation> reservationList = reservationServiceImpl.findAll();
    	assertEquals(3, reservationList.size());
    } 
    
    @Test
    public void findById() {
    	assertEquals(reservationServiceImpl.findById(1), reservation);
    }  
    
    @Test
    public void reservationListNotArchived() {
    	assertEquals(reservationServiceImpl.reservationListNotArchived(false), reservationList);
    }
    
    @Test
    public void checkIfWeStayInTheSameMonth() {
    	assertEquals(reservationServiceImpl.checkIfWeStayInTheSameMonth(1, 1, 29), "29.01");
    	assertEquals(reservationServiceImpl.checkIfWeStayInTheSameMonth(1, 1, 28), "01.02");
    }
    
    @Test
    public void add28daysToTheReservationDateEnd() {
    	String result1 = reservationServiceImpl.add28daysToTheReservationDateEnd(1, 1, 1950);
    	assertEquals("29.01.1950", result1);
    	
    	String result2 = reservationServiceImpl.add28daysToTheReservationDateEnd(20, 1, 1950);
    	assertEquals("17.02.1950", result2); 
    	
    	String result3 = reservationServiceImpl.add28daysToTheReservationDateEnd(20, 12, 1950);
    	assertEquals("17.01.1951", result3);   	
    }
    
    @Test
    public void lastRevervationForEachBooks() {
    	assertEquals(reservationServiceImpl.lastRevervationForEachBooks().size(), 0);
    }
    
    @Test
    public void getNearestDate() {
    	List<Date> dateList = new ArrayList<>();
    	Date date1 = new Date();
    	date1.setYear(2010);
    	date1.setMonth(5);
    	date1.setDate(5);
    	dateList.add(date1);
    	Date date2 = new Date();  	
    	date2.setYear(2015);
    	date2.setMonth(10);
    	date2.setDate(10);
    	dateList.add(date2);   	
    	assertEquals(reservationServiceImpl.getNearestDate(dateList, new Date()), date1);
    }
    
    @Test
    public void saveExtendReservation() {
    	reservationServiceImpl.saveExtendReservation(1);
    }
}
