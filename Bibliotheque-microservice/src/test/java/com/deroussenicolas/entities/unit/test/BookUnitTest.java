package com.deroussenicolas.entities.unit.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Book;

@SpringBootTest
public class BookUnitTest {

	private static Book book;
	private static String book_name = "Bonjour";
	private static String book_author = "José";
	private static String book_editor = "Karma";
	
    @BeforeClass
    public static void initBeforeClass() {
        book = new Book(book_name, book_author, book_editor);
    }
    
    @Test
    public void constructorWithDefaultValues() {
    	assertEquals(book.getBook_name(), book_name);
    	assertEquals(book.getBook_author(), book_author);
    	assertEquals(book.getBook_editor(), book_editor);
    }
    
    @Test
    public void checkCopyListOfTheBook() {
    	assertEquals(book.getCopy_list(), null);
    }   
    
    @Test
    public void checkWaiting_list_reservationOfTheBook() {
    	assertEquals(book.getWaiting_list_reservation(), null);
    }      
    
}
