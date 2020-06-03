package com.deroussenicolas.beans;

import java.io.Serializable;

public class WaitingListReservationBean implements Serializable	 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id_waiting_list_reservation;
	private UserBean user;
	private BookBean book;
	private int position_in_queue;
	private boolean is_archived;
	private boolean is_canceled;
	
	public WaitingListReservationBean() {
		
	}
	public int getId_waiting_list_reservation() {
		return id_waiting_list_reservation;
	}
	
	public void setId_waiting_list_reservation(int id_waiting_list_reservation) {
		this.id_waiting_list_reservation = id_waiting_list_reservation;
	}
	
	public UserBean getUser() {
		return user;
	}
	
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	public BookBean getBook() {
		return book;
	}
	
	public void setBook(BookBean book) {
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
