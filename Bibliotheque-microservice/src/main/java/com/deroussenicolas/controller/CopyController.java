package com.deroussenicolas.controller;


import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deroussenicolas.entities.Copy;
import com.deroussenicolas.entities.Reservation;
import com.deroussenicolas.service.CopyService;
import com.deroussenicolas.service.ReservationService;
import com.deroussenicolas.service.UserService;

@RestController
public class CopyController {
	

	@Autowired
	private CopyService copyService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/Copies")
    public @ResponseBody List<Copy> listOfAllCopies() {
		List<Copy> listCopy = copyService.findAll();
        return listCopy;
    }
	
    @GetMapping(value = "/Copy/{id}")
    public Copy oneCopyWithId(@PathVariable int id) {
    	return copyService.findById(id);
    }
     
	@GetMapping(value="/CopiesAvailable")
    public @ResponseBody List<Copy> listOfAllCopiesAvailable() {
        return copyService.findCopiesAvailable('0');
    }
	
    @GetMapping(value = "/CopyOfReservation/{id}")
    public Copy oneCopyOfReservationWithReservationId(@PathVariable int id) { 	
    	try {
        	if(reservationService.findById(id).getCopy() != null) {
        		return reservationService.findById(id).getCopy();
        	}
		} catch (Exception e) {
			System.err.println("error " + e);
		}
    	return null;
    }
	
    @GetMapping(value = "/CopiesOfUser/{email}")
    public List<Copy> allCopiesWithUserEmail(@PathVariable String email){
    	List <Reservation> reservationsList = reservationService.reservationListOfUser(userService.findByEmail(email).getId_user());
    	List <Integer> copy_id = new ArrayList<>();
    	for (Reservation reservation : reservationsList) {
    		copy_id.add(reservation.getCopy().getId_copy());
		}
    	List<Copy> copies = new ArrayList<>();
    	for (int i = 0 ; i < copy_id.size() ; i++) {
    		copies.add(i, copyService.findById(copy_id.get(i)));
    	}
    	return copies;  	
    }
	
}
