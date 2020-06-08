package com.deroussenicolas.service;

import java.util.List;

import com.deroussenicolas.entities.WaitingListReservation;

public interface WaitingListService {

	List<WaitingListReservation> findAll();

	List<Boolean> checkIfUserHasMadeAReservationForEchBooks(List<Boolean> checkingList, int user_id);
}
