package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.WaitingListReservationServiceImpl;

@SpringBootTest
public class WaitingListServiceUnitTest {

	private static WaitingListReservationServiceImpl waitingListReservationServiceImpl = mock(WaitingListReservationServiceImpl.class);
	private static List<WaitingListReservation> waitingListReservationList;
	
    @BeforeClass
    public static void initBeforeClass() {
    	creatingTheWaitingListReservationList();
    	given(waitingListReservationServiceImpl.findAll()).willReturn(waitingListReservationList);
    }
    
    private static void creatingTheWaitingListReservationList() {
    	waitingListReservationList = new ArrayList<>();
    	waitingListReservationList.add(new WaitingListReservation());
    	waitingListReservationList.add(new WaitingListReservation());
    	waitingListReservationList.add(new WaitingListReservation());
    }
    
    @Test
    public void findAll() {  	
    	List<WaitingListReservation> waitingListReservationList = waitingListReservationServiceImpl.findAll();
    	assertEquals(3, waitingListReservationList.size());
    } 
    
}
