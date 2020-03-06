package com.deroussenicolas.beans;

import java.io.Serializable;

import java.util.List;





public class CopyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_copy;
	private char status;
	private BookBean bookBean;
	private List <ReservationBean> reservation_list;
	
	public CopyBean() {
		super();
	}
	
	public int getId_copy() {
		return id_copy;
	}

	public void setId_copy(int id_copy) {
		this.id_copy = id_copy;
	}

	public char getStatus() {
		return status;
	}
	
	public void setStatus(char status) {
		this.status = status;
	}
	public BookBean getBookBean() {
		return bookBean;
	}

	public void setBookBean(BookBean bookBean) {
		this.bookBean = bookBean;
	}
	
	public List<ReservationBean> getReservation_list() {
		return reservation_list;
	}

	public void setReservation_list(List<ReservationBean> reservation_list) {
		this.reservation_list = reservation_list;
	}

	@Override
	public String toString() {
		return "CopyBean [id_copy=" + id_copy + ", status=" + status + ", bookBean=" + bookBean + ", reservation_list="
				+ reservation_list + "]";
	}

}
