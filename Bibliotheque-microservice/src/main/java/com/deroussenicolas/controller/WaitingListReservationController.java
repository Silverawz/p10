package com.deroussenicolas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deroussenicolas.entities.WaitingListReservation;
import com.deroussenicolas.service.WaitingListService;

@RestController
public class WaitingListReservationController {

	@Autowired
	private WaitingListService waitingListService;
	
	@GetMapping(value="/WaitingList")
    public @ResponseBody List<WaitingListReservation> listOfAllCopies() {
		List<WaitingListReservation> waitingListReservation = waitingListService.findAll();
        return waitingListReservation;
    }
}
