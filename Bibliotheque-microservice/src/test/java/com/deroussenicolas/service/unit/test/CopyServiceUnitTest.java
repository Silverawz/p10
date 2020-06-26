package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.dao.BookRepository;
import com.deroussenicolas.dao.CopyRepository;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.service.CopyServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CopyServiceUnitTest {
	
	@InjectMocks
	private static CopyServiceImpl copyServiceImpl;
	private static CopyRepository copyRepository = mock(CopyRepository.class);
	private static BookRepository bookRepository = mock(BookRepository.class);
	private static List<Copy> copyList;
	private static Copy copy = new Copy();
	
    @BeforeClass
    public static void initBeforeClass() {
    	copyServiceImpl = new CopyServiceImpl();
    	creatingTheCopyList();
    	given(copyRepository.findAll()).willReturn(copyList);
    	given(copyRepository.findById(1)).willReturn(copy);
    	given(copyRepository.findCopiesAvailable('0')).willReturn(copyList);
    	given(copyRepository.findCopiesAvailableByBookId('0', 1)).willReturn(copyList);
    }
    
    private static void creatingTheCopyList() {
    	copyList = new ArrayList<>();
    	copyList.add(new Copy()); 
    	copyList.add(new Copy()); 
    	copyList.add(new Copy());
    }
    
    
    @Test
    public void findAll() {  	
    	List<Copy> copyList = copyServiceImpl.findAll();
    	assertEquals(3, copyList.size());
    } 
    
    @Test
    public void findCopiesAvailable() {
    	List<Copy> copyList = copyServiceImpl.findCopiesAvailable('0');
    	assertEquals(3, copyList.size());
    }
    
    @Test
    public void findById() {
    	assertEquals(copyServiceImpl.findById(1), copy);
    }
    
    @Test
    public void numberOfCopiesNotAvailableForEachBook() {
    	copyServiceImpl.numberOfCopiesNotAvailableForEachBook();
    }
    
    @Test
    public void findCopiesAvailableByBookId() {
    	assertEquals(copyServiceImpl.findCopiesAvailableByBookId('0', 1), copyList);
    }
}
