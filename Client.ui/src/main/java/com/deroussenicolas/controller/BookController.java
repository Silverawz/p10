package com.deroussenicolas.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.deroussenicolas.beans.BookPresentationBean;
import com.deroussenicolas.beans.ReservationBean;
import com.deroussenicolas.beans.WaitingListReservationBean;
import com.deroussenicolas.proxies.MicroserviceBookProxy;
import com.deroussenicolas.proxies.MicroserviceCopyProxy;
import com.deroussenicolas.proxies.MicroserviceReservationProxy;
import com.deroussenicolas.proxies.MicroserviceUserProxy;
import com.deroussenicolas.proxies.MicroserviceWaitingListReservationProxy;

@Controller
@SessionAttributes("userEmail")
public class BookController {

	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MicroserviceBookProxy microServiceBookProxy;

	@Autowired
	private MicroserviceCopyProxy microServiceCopyProxy;
	
	@Autowired
	private MicroserviceReservationProxy microServiceReservationProxy;
	
	@Autowired
	private MicroserviceWaitingListReservationProxy microserviceWaitingListReservationProxy;
	
	  @GetMapping("/bookList") 
	  public ModelAndView bookList(ModelAndView modelAndView,
				@RequestParam(name="keyWord", defaultValue= "") String keyWord,
				@RequestParam(name="checkbox_id_book", defaultValue= "off") String checkbox_id_book,
				@RequestParam(name="checkbox_name_book", defaultValue= "off") String checkbox_name_book,
				@RequestParam(name="checkbox_number_copies", defaultValue= "off") String checkbox_number_copies,
				@RequestParam(name="checkbox_author_book", defaultValue= "off") String checkbox_author_book,
				@RequestParam(name="checkbox_editor_book", defaultValue= "off") String checkbox_editor_book,
				@RequestParam(name="checkbox_date_back", defaultValue= "off") String checkbox_date_back,
				@RequestParam(name="checkbox_waiting_queue", defaultValue= "off") String checkbox_waiting_queue,
				@RequestParam(name="checkbox_reserving", defaultValue= "off") String checkbox_reserving,
				@SessionAttribute("userEmail") String userEmail) { 		  
	  ModelAndView modelView = new ModelAndView(); 
	  List<BookBean> bookBeanList = microServiceBookProxy.listOfAllBooks(); 
	  List<BookBean> finalBookBeanListWithParameters = new ArrayList<>();	
	  int id_user = microserviceUserProxy.loadUserByUsername(userEmail).getId_user();
	  List<Boolean> booksOwnedByUserInOrderAsBoolean = microserviceUserProxy.booksOwnedByUserInOrderAsBoolean(id_user);
	  List<Date> lastReservationForEachBooks = microServiceBookProxy.lastRevervationForEachBooks();
	  List<Integer> queueSizeForEachsBooks = microServiceBookProxy.queueSizeForEachsBooks();
	  List<Integer> numberOfCopiesNotAvailableForEachBook = microServiceCopyProxy.numberOfCopiesNotAvailableForEachBook();
	  int index = 0;
	  for (BookBean bookBean : bookBeanList) {
		  bookBean.setDate_when_book_is_back(lastReservationForEachBooks.get(index));  
		  bookBean.setWaiting_queue(""+queueSizeForEachsBooks.get(index)+"/"+numberOfCopiesNotAvailableForEachBook.get(index)+"");
		  if(queueSizeForEachsBooks.get(index) == numberOfCopiesNotAvailableForEachBook.get(index)) { // if waiting queue is full then you cannot do a reservation
			  bookBean.setBook_is_already_reserved_by_user(true);
		  } else {
			  bookBean.setBook_is_already_reserved_by_user(booksOwnedByUserInOrderAsBoolean.get(index));
		  }
		  System.out.println(bookBean.isBook_is_already_reserved_by_user());
		  index++;
	  }
	  
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
					String dateAsString = null;
					if(bookBean.getDate_when_book_is_back() != null) {
						DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
						dateAsString = simpleDateFormat.format(bookBean.getDate_when_book_is_back());
						if(dateAsString.contains(keyWord) && !checkbox_date_back.equals("off")) {
							if(!(finalBookBeanListWithParameters.contains(bookBean))) {
								finalBookBeanListWithParameters.add(bookBean);
							}
						}
					} else if (keyWord.contains("Disponible")) {
						finalBookBeanListWithParameters.add(bookBean);
					}

					if(bookBean.getWaiting_queue().equals(keyWord) && !checkbox_waiting_queue.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}
					}
					//String booleanAsString = Boolean.toString(bookBean.isBook_is_already_reserved_by_user());
					if(bookBean.isBook_is_already_reserved_by_user() && !checkbox_reserving.equals("off")) {
						if(!(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);
						}
					}		
					
					if(checkbox_id_book.equals("off") && checkbox_name_book.equals("off") && checkbox_number_copies.equals("off")
					&& checkbox_author_book.equals("off") && checkbox_editor_book.equals("off") && checkbox_date_back.equals("off") 
					&& checkbox_waiting_queue.equals("off") && checkbox_reserving.equals("off")) {					
						if(Integer.toString(bookBean.getId_book()).contains(keyWord) || bookBean.getBook_name().contains(keyWord) ||
						Integer.toString(bookBean.getCopy_list().size()).contains(keyWord) || bookBean.getBook_author().contains(keyWord) ||
						bookBean.getBook_editor().contains(keyWord) || dateAsString.contains(keyWord) || bookBean.getWaiting_queue().equals(keyWord)
						&& !(finalBookBeanListWithParameters.contains(bookBean))) {
							finalBookBeanListWithParameters.add(bookBean);					
						}		
					}
			}
			modelView.addObject("booklist", finalBookBeanListWithParameters);
	  }	    
	  else {
		 // modelView.addObject("booklist", bookBeanList); 	
		 modelView.addObject("booklist", bookBeanList);
	  }
	  
	  
	  // avoir les trois elements suivants ici :
	  // 1 reserver un exemplaire en boolean, 
	  // 2 date de retour prévenue pour l'exemplaire le plus proche de la date actuel pour un livre
	  // 3 et enfin la file d'attente du livre en question
	  
	  
	  
	  /*   1
	  int id_user = microserviceUserProxy.loadUserByUsername(userEmail).getId_user();
	  List<Boolean> booksOwnedByUserInOrderAsBoolean = microserviceUserProxy.booksOwnedByUserInOrderAsBoolean(id_user);
	  


			2
	  List<Date> lastReservationForEachBooks = microServiceBookProxy.lastRevervationForEachBooks();
	  
	        3
	        
	  List<Integer> queueSizeForEachsBooks = microServiceBookProxy.queueSizeForEachsBooks();
		
	  */


	  modelView.addObject("keyWord", keyWord);
	  modelView.addObject("checkbox_id_book", checkbox_id_book);
	  modelView.addObject("checkbox_name_book", checkbox_name_book);
	  modelView.addObject("checkbox_number_copies", checkbox_number_copies);
	  modelView.addObject("checkbox_author_book", checkbox_author_book);
	  modelView.addObject("checkbox_editor_book", checkbox_editor_book);
	  modelView.addObject("checkbox_date_back", checkbox_date_back);
	  modelView.addObject("checkbox_waiting_queue", checkbox_waiting_queue);
	  modelView.addObject("checkbox_reserving", checkbox_reserving);
	  modelView.setViewName("book/booklist"); 	  
	  return modelView; 	  
	  }
	
	  
	 public List<BookPresentationBean> listOfBooksBeanFormatedForPresentation(List<BookBean> bookBeanList, String userEmail) {
		 
		 /*
		List<CopyBean> listCopy = microServiceCopyProxy.allCopiesWithUserEmail(userEmail);
		for (CopyBean copyBean : listCopy) {
			if(copyBean.getStatus())
		}*/
		
		 //recuperer une liste de reservation = au nombre de livre avec leur date de retour la plus proche
		List<ReservationBean> listAllReservationsNotArchived = microServiceReservationProxy.listOfAllReservationNotArchived();
		
		List<BookPresentationBean> listOfBooksBeansFormatedForPresentation = new ArrayList<>();
		
		
		for (BookBean bookBean : bookBeanList) {
			BookPresentationBean bookPresentationBean = new BookPresentationBean(bookBean.getId_book(), bookBean.getBook_name(), bookBean.getBook_author(), 
					 bookBean.getBook_editor(), bookBean.getCopy_list());
			//si copy = 0 alors on peut reservé, 
			if(bookBean.getCopy_list().size() == 0) {
				
				//reste à check si l'user possède un exemplaire pour valider le true		
				bookPresentationBean.setIs_reserved(true);
			}
			
			//comment faire ?
			bookPresentationBean.setDate_when_book_is_back("15/02/2020");

			
			listOfBooksBeansFormatedForPresentation.add(bookPresentationBean);
		}
		return listOfBooksBeansFormatedForPresentation;		 
	 }

	 
	 
	 
	  
	  /*
	  public List<Integer> bookListWaitingQueue(List<BookBean> bookList) {
		List <WaitingListReservationBean> list = microserviceWaitingListReservationProxy.waitingList();
		return null;	  
	  }*/
}
