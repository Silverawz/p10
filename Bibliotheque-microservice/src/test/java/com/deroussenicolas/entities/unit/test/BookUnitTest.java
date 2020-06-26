package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Book;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.WaitingListReservation;

@SpringBootTest
public class BookUnitTest {

	private static Book book;
	private static String book_name = "Bonjour";
	private static String book_author = "José";
	private static String book_editor = "Karma";
	private static List<Copy> copyList;
	private static List<WaitingListReservation> waitingListReservationList;
	
    @BeforeClass
    public static void initBeforeClass() {
    	copyList = new ArrayList<>();
    	waitingListReservationList = new ArrayList<>();
        book = new Book(book_name, book_author, book_editor);
    }
    
    @Test
    public void constructorWithDefaultValues() {
    	assertEquals(book.getBook_name(), book_name);
    	assertEquals(book.getBook_author(), book_author);
    	assertEquals(book.getBook_editor(), book_editor);
    	book.setBook_name("foret");
    	book.setBook_author("andre");
    	book.setBook_editor("lune");
    	assertEquals(book.getBook_name(), "foret");
    	assertEquals(book.getBook_author(), "andre");
    	assertEquals(book.getBook_editor(), "lune");
    }
    
    @Test
    public void checkCopyListOfTheBook() {
    	assertEquals(book.getCopy_list(), null);
    	copyList.add(new Copy());
    	book.setCopy_list(copyList);
    	assertEquals(book.getCopy_list(), copyList);
    }   
    
    @Test
    public void checkWaiting_list_reservationOfTheBook() {
    	assertEquals(book.getWaiting_list_reservation(), null);
    	waitingListReservationList.add(new WaitingListReservation());
    	book.setWaiting_list_reservation(waitingListReservationList);
    	assertEquals(book.getWaiting_list_reservation(), waitingListReservationList);
    }      
    
}
