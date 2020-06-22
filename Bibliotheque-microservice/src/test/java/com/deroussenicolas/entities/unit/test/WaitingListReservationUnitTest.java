package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.User;
import com.deroussenicolas.entities.WaitingListReservation;

@SpringBootTest
public class WaitingListReservationUnitTest {

	private static WaitingListReservation waitingListReservation;
	private static Book book;
	private static User user;
	private static User waitingListReservation_user = user;
	private static Book waitingListReservation_book = book;
	private static int waitingListReservation_position_in_queue = 1;
	private static boolean waitingListReservation_is_archived = false;
	private static boolean waitingListReservation_is_canceled = false;
	
    @BeforeClass
    public static void initBeforeClass() {
    	waitingListReservation = new WaitingListReservation(waitingListReservation_user, waitingListReservation_book, waitingListReservation_position_in_queue
    			, waitingListReservation_is_archived, waitingListReservation_is_canceled);
    }
    
    @Test
    public void constructorWithDefaultValues() {
    	assertEquals(waitingListReservation.getDate_mail_send(), null);
    	assertEquals(waitingListReservation.getPosition_in_queue(), waitingListReservation_position_in_queue);
    	assertEquals(waitingListReservation.getUser(), waitingListReservation_user);
    }
}
