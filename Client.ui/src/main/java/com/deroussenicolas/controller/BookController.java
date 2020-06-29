package com.deroussenicolas.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.BookBean;
import com.deroussenicolas.beans.CopyBean;
import com.deroussenicolas.proxies.MicroserviceBookProxy;
import com.deroussenicolas.proxies.MicroserviceCopyProxy;
import com.deroussenicolas.proxies.MicroserviceUserProxy;

@Controller
@SessionAttributes("userEmail")
public class BookController {

	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MicroserviceBookProxy microServiceBookProxy;

	@Autowired
	private MicroserviceCopyProxy microServiceCopyProxy;
	
	
	  @GetMapping("/bookList") 
	  public ModelAndView bookList(ModelAndView modelAndView,
				@RequestParam(name="keyWord", defaultValue= "") String keyWord,
				@RequestParam(name="checkbox_name_book", defaultValue= "off") String checkbox_name_book,
				@RequestParam(name="checkbox_number_copies", defaultValue= "off") String checkbox_number_copies,
				@RequestParam(name="checkbox_author_book", defaultValue= "off") String checkbox_author_book,
				@RequestParam(name="checkbox_editor_book", defaultValue= "off") String checkbox_editor_book,
				@SessionAttribute("userEmail") String userEmail) { 		  
	  ModelAndView modelView = new ModelAndView();   
	  List<BookBean> bookBeanList = microServiceBookProxy.listOfAllBooks(); 
	  bookBeanList =  insertTheDataInsideTheBookBean(bookBeanList, userEmail);
	  List<BookBean> finalBookBeanListWithParameters = new ArrayList<>();	
	  if(!keyWord.equals("")) {
			for (BookBean bookBean : bookBeanList) {							
					if(bookBean.getBook_name().contains(keyWord) && !checkbox_name_book.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}	
					}			
					if(Integer.toString(bookBean.getNumberOfCopiesAvailable()).contains(keyWord) && !checkbox_number_copies.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}	
					}			
					if(bookBean.getBook_author().contains(keyWord) && !checkbox_author_book.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}	
					}			
					if(bookBean.getBook_editor().contains(keyWord) && !checkbox_editor_book.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);				
						}	
					}			
					
					if(checkbox_name_book.equals("off") && checkbox_number_copies.equals("off")
					&& checkbox_author_book.equals("off") && checkbox_editor_book.equals("off")) {					
						if(bookBean.getBook_name().contains(keyWord) || Integer.toString(bookBean.getCopy_list().size()).contains(keyWord) 
						|| bookBean.getBook_author().contains(keyWord) || bookBean.getBook_editor().contains(keyWord) 
						&& !(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);					
						}		
					}
			}
			modelView.addObject("booklist", finalBookBeanListWithParameters);
	  }	    
	  else {
		 modelView.addObject("booklist", bookBeanList);
	  }
	  modelView.addObject("keyWord", keyWord);
	  modelView.addObject("checkbox_name_book", checkbox_name_book);
	  modelView.addObject("checkbox_number_copies", checkbox_number_copies);
	  modelView.addObject("checkbox_author_book", checkbox_author_book);
	  modelView.addObject("checkbox_editor_book", checkbox_editor_book);
	  modelView.setViewName("book/booklist"); 	  
	  return modelView; 	  
	  }
	
	  
	  
	  
	  private List<BookBean> insertTheDataInsideTheBookBean(List<BookBean> bookBeanList, String userEmail) {  
		  int id_user = microserviceUserProxy.loadUserByUsername(userEmail).getId_user();
		  List<Boolean> booksOwnedByUserInOrderAsBoolean = microserviceUserProxy.booksOwnedByUserInOrderAsBoolean(id_user);
		  List<Date> lastReservationForEachBooks = microServiceBookProxy.lastRevervationForEachBooks();
		  List<Integer> queueSizeForEachsBooks = microServiceBookProxy.queueSizeForEachsBooks();
		  List<Integer> numberOfCopiesNotAvailableForEachBook = microServiceCopyProxy.numberOfCopiesNotAvailableForEachBook();		  
		  for (int i = 0; i < bookBeanList.size(); i++) {
			// date_when_book_is_back
			bookBeanList.get(i).setDate_when_book_is_back(lastReservationForEachBooks.get(i)); 
			// waiting_queue
			if(queueSizeForEachsBooks.get(i) > 0 && numberOfCopiesNotAvailableForEachBook.get(i) == 0) {
				bookBeanList.get(i).setWaiting_queue("0/0"); 
			} else {
				bookBeanList.get(i).setWaiting_queue(""+queueSizeForEachsBooks.get(i)+"/"+numberOfCopiesNotAvailableForEachBook.get(i)+""); 
			}		
			// book_is_already_reserved_by_user
			if(queueSizeForEachsBooks.get(i) == numberOfCopiesNotAvailableForEachBook.get(i)) { // if waiting queue is full then you cannot do a reservation
				  bookBeanList.get(i).setBook_is_already_reserved_by_user(true); // cannot reserve because waiting queue is full
			} else {
				  bookBeanList.get(i).setBook_is_already_reserved_by_user(booksOwnedByUserInOrderAsBoolean.get(i)); // insert the previous data
			}
			// numberOfCopiesAvailable	
		    bookBeanList.get(i).setNumberOfCopiesAvailable(numberOfCopiesAvailableCalculate(bookBeanList.get(i).getCopy_list()));	  
		  }  
		  return bookBeanList;	  
	  }
	  
	  private int numberOfCopiesAvailableCalculate(List<CopyBean> copyList) {
		 int numberOfCopiesAvailable = 0; 
		 for (CopyBean copyBean : copyList) {
			if(copyBean.getStatus() == '0') {
				numberOfCopiesAvailable++;
			}	
		 } 	 
		 return numberOfCopiesAvailable;
	  }
}
