package com.deroussenicolas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deroussenicolas.dao.WaitingListReservationRepository;
import com.deroussenicolas.entities.WaitingListReservation;

@Service("WaitingListServiceImpl")
public class WaitingListReservationServiceImpl implements WaitingListService {

	@Autowired
	private WaitingListReservationRepository waitingListRepository;

	@Override
	public List<WaitingListReservation> findAll() {
		return waitingListRepository.findAll();
	}
	
	
}
