package com.deroussenicolas.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.service.CopyServiceImpl;

@SpringBootTest
public class CopyServiceUnitTest {
	
	
	private static CopyServiceImpl copyServiceImpl = mock(CopyServiceImpl.class);
	private static List<Copy> copyList;
	private static Copy copy = new Copy();
	
    @BeforeClass
    public static void initBeforeClass() {
    	creatingTheCopyList();
    	given(copyServiceImpl.findAll()).willReturn(copyList);
    	given(copyServiceImpl.findById(1)).willReturn(copy);
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
    public void findById() {
    	assertEquals(copyServiceImpl.findById(1), copy);
    }
}
