package com.deroussenicolas.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deroussenicolas.beans.BookBean;
import com.deroussenicolas.proxies.MicroserviceBookProxy;

@Controller
@SessionAttributes("userEmail")
public class BookController {


	@Autowired
	private MicroserviceBookProxy microServiceBookProxy;

	
	  @GetMapping("/bookList") 
	  public ModelAndView bookList(ModelAndView modelAndView,
				@RequestParam(name="keyWord", defaultValue= "") String keyWord,
				@RequestParam(name="checkbox_id_book", defaultValue= "off") String checkbox_id_book,
				@RequestParam(name="checkbox_name_book", defaultValue= "off") String checkbox_name_book,
				@RequestParam(name="checkbox_number_copies", defaultValue= "off") String checkbox_number_copies,
				@RequestParam(name="checkbox_author_book", defaultValue= "off") String checkbox_author_book,
				@RequestParam(name="checkbox_editor_book", defaultValue= "off") String checkbox_editor_book) { 		  
	  ModelAndView modelView = new ModelAndView(); 
	  List<BookBean> bookBeanList = microServiceBookProxy.listOfAllBooks(); 
	  List<BookBean> finalBookBeanListWithParameters = new ArrayList<>();		  
	  if(!keyWord.equals("")) {
			for (BookBean bookBean : bookBeanList) {				
					if(Integer.toString(bookBean.getId_book()).contains(keyWord) && !checkbox_id_book.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}	
					}			
					if(bookBean.getBook_name().contains(keyWord) && !checkbox_name_book.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}	
					}			
					if(Integer.toString(bookBean.getCopy_list().size()).contains(keyWord) && !checkbox_number_copies.equals("off")) {
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
					if(checkbox_id_book.equals("off") && checkbox_name_book.equals("off") && checkbox_number_copies.equals("off")
					&& checkbox_author_book.equals("off") && checkbox_editor_book.equals("off")) {					
						if(Integer.toString(bookBean.getId_book()).contains(keyWord) || bookBean.getBook_name().contains(keyWord) ||
						Integer.toString(bookBean.getCopy_list().size()).contains(keyWord) || bookBean.getBook_author().contains(keyWord) ||
						bookBean.getBook_editor().contains(keyWord) && !(finalBookBeanListWithParameters.contains(bookBean))) {
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
	  modelView.addObject("checkbox_id_book", checkbox_id_book);
	  modelView.addObject("checkbox_name_book", checkbox_name_book);
	  modelView.addObject("checkbox_number_copies", checkbox_number_copies);
	  modelView.addObject("checkbox_author_book", checkbox_author_book);
	  modelView.addObject("checkbox_editor_book", checkbox_editor_book);
	  modelView.setViewName("book/booklist"); 	  
	  return modelView; 	  
	  }
	

}
