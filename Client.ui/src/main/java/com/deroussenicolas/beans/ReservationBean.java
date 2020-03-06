package com.deroussenicolas.beans;

import java.io.Serializable;

public class ReservationBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_reservation;
	private String date_begin;
	private String date_end;
	private boolean is_archived;
	private CopyBean copy;
	private UserBean user;
	private String book_name;
	private char status;
	
	public ReservationBean() {
		super();
	}
	
	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public int getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(int id_reservation) {
		this.id_reservation = id_reservation;
	}

	public String getDate_begin() {
		return date_begin;
	}

	public void setDate_begin(String date_begin) {
		this.date_begin = date_begin;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public boolean isIs_archived() {
		return is_archived;
	}

	public void setIs_archived(boolean is_archived) {
		this.is_archived = is_archived;
	}

	public CopyBean getCopy() {
		return copy;
	}

	public void setCopy(CopyBean copy) {
		this.copy = copy;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ReservationBean [id_reservation=" + id_reservation + ", date_begin=" + date_begin + ", date_end="
				+ date_end + ", is_archived=" + is_archived + ", copy=" + copy + ", user=" + user + "]";
	}

}
