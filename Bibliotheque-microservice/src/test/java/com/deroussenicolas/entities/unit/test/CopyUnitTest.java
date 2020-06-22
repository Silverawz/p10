package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Copy;

@SpringBootTest
public class CopyUnitTest {

	private static Copy copy;
	private static Book book;
	private static char copy_status = '0';
			
	@BeforeClass
	public static void initBeforeClass() {
		copy = new Copy(copy_status, book);
	}
	
    @Test
    public void constructorWithDefaultValues() {
    	assertEquals(copy.getStatus(), copy_status);
    	assertEquals(copy.getBook(), book);
    }
    
    @Test
    public void checkReservationListForTheCopy() {
    	assertEquals(copy.getReservation_list(), null);
    }   
    
	
}
