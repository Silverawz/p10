package com.deroussenicolas.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.BookService;
import com.deroussenicolas.service.CopyService;
import com.deroussenicolas.service.UserService;
import com.deroussenicolas.service.WaitingListService;

@RestController
public class WaitingListReservationController {

	@Autowired
	private WaitingListService waitingListService;
	@Autowired
	private WaitingListReservationRepository waitingListReservationRepository;
	@Autowired 
	private BookService bookService;
	@Autowired 
	private CopyService copyService;
	@Autowired 
	private UserService userService;
	
	@GetMapping(value="/WaitingList")
    public @ResponseBody List<WaitingListReservation> listOfAllCopies() {
		List<WaitingListReservation> waitingListReservation = waitingListService.findAll();
        return waitingListReservation;
    }
	
	@GetMapping(value="/WaitingListReservationCheck/{id_book}/{userEmail}")
    public boolean WaitingListReservationCheck(@PathVariable int id_book, @PathVariable String userEmail) {
    	if(bookService.findById(id_book) == null || userService.findByEmail(userEmail) == null) {
    		return false;
    	}
    	// check if zero copies are available
    	List<Integer> resultList = checkIfNoCopiesAreAvailable(id_book);
    	if(resultList.get(0) == 0) {
    		return false;
    	}
    	// check if the waitingQueue is full
    	int numberOfRervationWaitingListForABookNotArchivedNorCanceled = numberOfRervationWaitingListForABookNotArchivedNorCanceled(id_book);
    	if((resultList.get(1)*2) != numberOfRervationWaitingListForABookNotArchivedNorCanceled) {
    		WaitingListReservation waitingListReservation = new WaitingListReservation();
    		waitingListReservation.setBook(bookService.findById(id_book));
    		waitingListReservation.setUser(userService.findByEmail(userEmail));   		
    		waitingListReservation.setIs_archived(false);
    		waitingListReservation.setIs_canceled(false);
    		waitingListReservation.setPosition_in_queue(positionInQueueCalculate(id_book));;
    		waitingListReservationRepository.save(waitingListReservation);
    		return true;
    	}
    	return false;
    }
	
	
	private int positionInQueueCalculate(int id_book) {
		int positionInQueue = 0;
		List<WaitingListReservation> waitingListReservationOfBookWithParams = waitingListService.waitingListReservationOfBookWithParams(id_book, false, false);
		List<Integer> positionList = new ArrayList<>();
		if(!waitingListReservationOfBookWithParams.isEmpty()) {
			for (WaitingListReservation waitingListReservation : waitingListReservationOfBookWithParams) {
				positionList.add(waitingListReservation.getPosition_in_queue());
			}
			Collections.sort(positionList);
			positionInQueue = positionList.get(positionList.size() -1) + 1;
		}
		else {
			positionInQueue = 1;
		}		
		return positionInQueue;
	}
	
	
	
	
	private int numberOfRervationWaitingListForABookNotArchivedNorCanceled(int id_book) {
		int result = 0;
		waitingListReservationRepository.findAll();
		for (WaitingListReservation waitingListReservation : waitingListReservationRepository.findAll()) {
			if(waitingListReservation.isIs_archived() == false && waitingListReservation.isIs_canceled() == false && 
			waitingListReservation.getBook().getId_book() == id_book) {
				result++;
			}
		}
		return result;
	}
	

	private List<Integer> checkIfNoCopiesAreAvailable(int id_book) {
		List<Integer> resultList = new ArrayList<>();
		resultList.add(0); resultList.add(0);
		List<Copy> copies = copyService.findAll();
		for (Copy copy : copies) {
			if(copy.getBook().getId_book() == id_book && copy.getStatus() == '0') {
				return resultList;
			} else if (copy.getBook().getId_book() == id_book) {
				resultList.set(1, resultList.get(1) + 1);
			}
		}
		resultList.set(0, 1); // confirm there is no more copies available	
		return resultList;
	}
}
