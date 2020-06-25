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
import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.WaitingListReservationServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WaitingListServiceUnitTest {

	@InjectMocks
	private static WaitingListReservationServiceImpl waitingListReservationServiceImpl;
	private static WaitingListReservation waitingListReservation;
	private static List<WaitingListReservation> waitingListReservationList;
	private static WaitingListReservationRepository waitingListRepository = mock(WaitingListReservationRepository.class);
	
    @BeforeClass
    public static void initBeforeClass() {
    	waitingListReservationServiceImpl = new WaitingListReservationServiceImpl();
    	creatingTheWaitingListReservationList();
    	given(waitingListRepository.findAll()).willReturn(waitingListReservationList);
    	given(waitingListRepository.waitingListReservationOfBookWithParams(1, false, false)).willReturn(waitingListReservationList);
    	given(waitingListRepository.waitingListReservationOfUserBookWithParamsForASingleObject(1, 1, false, false)).willReturn(waitingListReservation);  	
    	given(waitingListRepository.waitingListReservationById(1)).willReturn(waitingListReservation);
    	given(waitingListRepository.waitingListReservationOfUserWithParamsArchived(1, false)).willReturn(waitingListReservationList);   	
    }
    
    private static void creatingTheWaitingListReservationList() {
    	waitingListReservationList = new ArrayList<>();
    	waitingListReservationList.add(new WaitingListReservation());
    	waitingListReservationList.add(new WaitingListReservation());
    	waitingListReservationList.add(new WaitingListReservation());
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
    
    
    
    
    // TO DO assez compliqué a faire
    @Test 
    public void checkIfUserHasMadeAReservationForEchBooks() {
    	
    	//waitingListReservationServiceImpl.checkIfUserHasMadeAReservationForEchBooks();
    	
    	
    }
    
}
