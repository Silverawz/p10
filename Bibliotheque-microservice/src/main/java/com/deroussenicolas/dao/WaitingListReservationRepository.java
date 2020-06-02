package com.deroussenicolas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deroussenicolas.entities.WaitingListReservation;

public interface WaitingListReservationRepository extends JpaRepository <WaitingListReservation, Long> {

}
