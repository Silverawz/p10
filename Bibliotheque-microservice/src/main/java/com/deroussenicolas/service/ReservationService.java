package com.deroussenicolas.service;


import java.util.List;

import com.deroussenicolas.entities.Reservation;

public interface ReservationService {

	Reservation saveExtendReservation(int id);

	List<Reservation> reservationListOfUser(int id_user);

	Reservation findById(int id);

	List<Reservation> findAll();

	List<Reservation> reservationListNotArchived(boolean b);
}
