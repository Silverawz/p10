package com.deroussenicolas.dao;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deroussenicolas.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation, Long>{
	
	@Query("from Reservation where user_id_user=?1 and is_archived=?2")
	List<Reservation> reservationListOfUser(int id_user, boolean is_archived);
	
	@Query("from Reservation where user_id_user=?1")
	List<Reservation> reservationListOfUser(int id_user);
	
	@Query("from Reservation where is_archived=?1")
	List<Reservation> reservationListNotArchived(boolean is_archived);
	
	@Query("from Reservation where id_reservation=?1")
	Reservation findById(int id);

	
}