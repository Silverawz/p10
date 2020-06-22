package com.deroussenicolas.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    	//check if the user doesnt have already a reservation for that book
    	int user_id = userService.findByEmail(userEmail).getId_user();
    	WaitingListReservation waitingListReservationAlreadyExisting = waitingListService.waitingListReservationOfUserBookWithParamsForASingleObject(user_id, id_book, false, false);
    	if((resultList.get(1)*2) != numberOfRervationWaitingListForABookNotArchivedNorCanceled && waitingListReservationAlreadyExisting == null) {
    		WaitingListReservation waitingListReservation = new WaitingListReservation();
    		waitingListReservation.setBook(bookService.findById(id_book));
    		waitingListReservation.setUser(userService.findByEmail(userEmail));   		
    		waitingListReservation.setIs_archived(false);
    		waitingListReservation.setIs_canceled(false);
    		waitingListReservation.setPosition_in_queue(positionInQueueCalculate(id_book));;
    		waitingListService.save(waitingListReservation);
    		return true;
    	}
    	return false;
    }
	
	@GetMapping(value="/WaitingListReservationFromUser/{userEmail}")
    public List<WaitingListReservation> WaitingListReservationFromUser(@PathVariable String userEmail) {
		int user_id = userService.findByEmail(userEmail).getId_user();
		return waitingListService.waitingListReservationOfUserWithParamsArchived(user_id, false);		
	}
 
	
	
	@GetMapping(value="/WaitingListReservationCancel/{id_waitingListReservation}/{userEmail}")
    public boolean WaitingListReservationCancel(@PathVariable int id_waitingListReservation, @PathVariable String userEmail) {	
		WaitingListReservation waitingListReservation = waitingListService.waitingListReservationById(id_waitingListReservation);
		if (waitingListReservation.getUser().getEmail() == userEmail || waitingListReservation.isIs_canceled() == false) {
			waitingListReservation.setIs_canceled(true);
			waitingListService.save(waitingListReservation);
			positionInQueueRecalculateAllForASingleBook(waitingListReservation);
			return true;
		} else {
			return false;
		}
	}

	private void positionInQueueRecalculateAllForASingleBook(WaitingListReservation waitingListReservation) {
		int id_book = waitingListReservation.getBook().getId_book();
		List<WaitingListReservation> waitingListReservationOfBookWithParams = waitingListService.waitingListReservationOfBookWithParams(id_book, false, false);
		List<String> listOfWaitingListReservationIdAndPositionInQueue = new ArrayList<>();		
		for (WaitingListReservation waitingListReservation2 : waitingListReservationOfBookWithParams) {
			listOfWaitingListReservationIdAndPositionInQueue.add(
					waitingListReservation2.getId_waiting_list_reservation()
					+"/"+
					waitingListReservation2.getPosition_in_queue());
		}
		int positionInTheQueue = 1;
		while (!listOfWaitingListReservationIdAndPositionInQueue.isEmpty()) {
			listOfWaitingListReservationIdAndPositionInQueue = assignNewPositionInQueueWithComparison(listOfWaitingListReservationIdAndPositionInQueue, positionInTheQueue);
			positionInTheQueue++;
		}
	}
	
	
	private List<String> assignNewPositionInQueueWithComparison(List<String> listOfWaitingListReservationIdAndPositionInQueue, int positionInTheQueue) {
		List<String> listToCompare = listOfWaitingListReservationIdAndPositionInQueue;
		int id_current_WaitingListReservation_closest_to_zero = 0;
		int position_in_queue_closest_to_zero = 0;		
		int index_of_lowest_position = 0;
		if (listToCompare.size() >= 2) {
			for (int i = 0; i < listToCompare.size(); i++) {
				String splitInformation[] = listToCompare.get(i).split("/");			
				int id_current_waitingListReservation = Integer.parseInt(splitInformation[0]);
				int position_in_queue_forCurrentReservation = Integer.parseInt(splitInformation[1]);
				if(position_in_queue_forCurrentReservation < position_in_queue_closest_to_zero || position_in_queue_closest_to_zero == 0) {			
					id_current_WaitingListReservation_closest_to_zero = id_current_waitingListReservation;
					position_in_queue_closest_to_zero = position_in_queue_forCurrentReservation;
					index_of_lowest_position = i;
				}
			}
			listToCompare.remove(index_of_lowest_position);
			WaitingListReservation waitingListReservation = waitingListService.waitingListReservationById(id_current_WaitingListReservation_closest_to_zero);
			waitingListReservation.setPosition_in_queue(positionInTheQueue);
			waitingListService.save(waitingListReservation);		
		} else if (listToCompare.size() == 1){			
			String splitInformation[] = listToCompare.get(0).split("/");			
			int id_current_waitingListReservation = Integer.parseInt(splitInformation[0]);
			WaitingListReservation waitingListReservation = waitingListService.waitingListReservationById(id_current_waitingListReservation);
			waitingListReservation.setPosition_in_queue(positionInTheQueue);
			waitingListService.save(waitingListReservation);	
			listToCompare.remove(0);
		}
		return listToCompare;	
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
		waitingListService.findAll();
		for (WaitingListReservation waitingListReservation : waitingListService.findAll()) {
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
