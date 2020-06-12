package com.deroussenicolas.service;

import java.util.List;

import com.deroussenicolas.entities.WaitingListReservation;

public interface WaitingListService {

	List<WaitingListReservation> findAll();

	List<Boolean> checkIfUserHasMadeAReservationForEchBooks(List<Boolean> checkingList, int user_id);
	
	List<WaitingListReservation> waitingListReservationOfBookWithParams(int book_id, boolean is_archived, boolean is_canceled);
	
	WaitingListReservation waitingListReservationOfUserBookWithParamsForASingleObject(int user_id, int book_id, boolean is_archived, boolean is_canceled);
}
