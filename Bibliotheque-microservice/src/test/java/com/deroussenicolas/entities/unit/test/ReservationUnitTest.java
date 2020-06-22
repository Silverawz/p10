package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.entities.User;

public class ReservationUnitTest {

	private static Reservation reservation;
	private static User user;
	private static Copy copy;
	private static String reservation_date_begin = "01.02.2019";
	private static String reservation_date_end = "02.03.2019";
	private static boolean reservation_is_archived = false;
	
    @BeforeClass
    public static void initBeforeClass() {
        reservation = new Reservation(reservation_date_begin, reservation_date_end, reservation_is_archived, copy, user);
    }
    
    @Test
    public void constructorWithDefaultValues() {
    	assertEquals(reservation.getDate_begin(), reservation_date_begin);
    	assertEquals(reservation.getDate_end(), reservation_date_end);
    }
    
    
    
}
