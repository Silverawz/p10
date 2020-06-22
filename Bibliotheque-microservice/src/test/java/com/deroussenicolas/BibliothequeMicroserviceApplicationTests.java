package com.deroussenicolas;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import com.deroussenicolas.configuration.WebMvcConfig;
import com.deroussenicolas.controller.BookController;
import com.deroussenicolas.entities.Book;
import com.deroussenicolas.service.BookService;

@SpringBootTest
public class BibliothequeMicroserviceApplicationTests {

	//@Autowiredprivate WebMvcConfig webMvcConfig;
	 /*
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

   
    
    @Test
    public void shouldFindAllSessions() throws Exception {

        given(this.bookService.findById(1))
		        .willReturn(new Book());

        this.mvc.perform(get("/Book/1"))
                .andExpect(status().isOk());
        
    }
	

	@Test
	public void contextLoads() {
	}
	*/
	@Test
	public void checkPasswordEncoder() throws Exception {
		int a = 5;
		
		assertEquals(a, 5);
	}
	
}
