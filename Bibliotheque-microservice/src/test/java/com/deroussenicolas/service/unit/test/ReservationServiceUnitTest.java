package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.service.ReservationServiceImpl;

@SpringBootTest
public class ReservationServiceUnitTest {

	private static ReservationServiceImpl reservationServiceImpl = mock(ReservationServiceImpl.class);
	private static ReservationServiceImpl reservationServiceImplNotMock; 
	private static List<Reservation> reservationList;
	private static Reservation reservation = new Reservation();
	
    @BeforeClass
    public static void initBeforeClass() {
    	reservationServiceImplNotMock = new ReservationServiceImpl();
    	creatingTheReservationList();
    	given(reservationServiceImpl.findAll()).willReturn(reservationList);
    	given(reservationServiceImpl.findById(1)).willReturn(reservation);
    }
    
    private static void creatingTheReservationList() {
    	reservationList = new ArrayList<>();
    	reservationList.add(new Reservation());
    	reservationList.add(new Reservation());
    	reservationList.add(new Reservation());
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
    public void add28daysToTheReservationDateEnd() {
    	String result1 = reservationServiceImplNotMock.add28daysToTheReservationDateEnd(1, 1, 1950);
    	assertEquals("29.01.1950", result1);
    	
    	String result2 = reservationServiceImplNotMock.add28daysToTheReservationDateEnd(20, 1, 1950);
    	assertEquals("17.02.1950", result2); 
    	
    	String result3 = reservationServiceImplNotMock.add28daysToTheReservationDateEnd(20, 12, 1950);
    	assertEquals("17.01.1951", result3);   	
    }
}
