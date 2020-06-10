package com.deroussenicolas.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deroussenicolas.entities.WaitingListReservation;
@Repository
public interface WaitingListReservationRepository extends JpaRepository <WaitingListReservation, Long> {

	@Query("from WaitingListReservation where is_archived=?1 and is_canceled=?2")
	List<WaitingListReservation> waitingListReservationWithParams(boolean is_archived, boolean is_canceled);
	
	@Query("from WaitingListReservation where user_id_user=?1 and is_archived=?2 and is_canceled=?3")
	List<WaitingListReservation> waitingListReservationOfUserWithParams(int user_id, boolean is_archived, boolean is_canceled);
	
	@Query("from WaitingListReservation where user_id_book=?1 and is_archived=?2 and is_canceled=?3")
	List<WaitingListReservation> waitingListReservationOfBookWithParams(int book_id, boolean is_archived, boolean is_canceled);
}
