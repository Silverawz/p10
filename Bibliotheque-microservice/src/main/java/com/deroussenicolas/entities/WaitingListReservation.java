package com.deroussenicolas.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="WaitingListReservation")
public class WaitingListReservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id_waiting_list_reservation;
	@ManyToOne
	private User user;
	@ManyToOne
	private Book book;
	@NotNull
	private int position_in_queue;
	@NotNull
	private boolean is_archived;
	@NotNull
	private boolean is_canceled;
	
	public WaitingListReservation() {
		
	}

	public int getId_waiting_list_reservation() {
		return id_waiting_list_reservation;
	}

	public void setId_waiting_list_reservation(int id_waiting_list_reservation) {
		this.id_waiting_list_reservation = id_waiting_list_reservation;
	}
	
	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonBackReference
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getPosition_in_queue() {
		return position_in_queue;
	}

	public void setPosition_in_queue(int position_in_queue) {
		this.position_in_queue = position_in_queue;
	}

	public boolean isIs_archived() {
		return is_archived;
	}

	public void setIs_archived(boolean is_archived) {
		this.is_archived = is_archived;
	}

	public boolean isIs_canceled() {
		return is_canceled;
	}

	public void setIs_canceled(boolean is_canceled) {
		this.is_canceled = is_canceled;
	}
	
	

}
