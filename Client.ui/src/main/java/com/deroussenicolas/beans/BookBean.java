package com.deroussenicolas.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class BookBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id_book;
	private String book_name;
	private String book_author;
	private String book_editor;
	private List <CopyBean> copy_list;
	private boolean book_is_already_reserved_by_user;
	private Date date_when_book_is_back;
	private String waiting_queue;
	private int numberOfCopiesAvailable;
	
	public BookBean() {
		super();
	}	

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	
	
	public String getBook_author() {
		return book_author;
	}

	public void setBook_author(String book_author) {
		this.book_author = book_author;
	}

	public String getBook_editor() {
		return book_editor;
	}

	public void setBook_editor(String book_editor) {
		this.book_editor = book_editor;
	}

	public List<CopyBean> getCopy_list() {
		return copy_list;
	}

	public void setCopy_list(List<CopyBean> copy_list) {
		this.copy_list = copy_list;
	}

	public boolean isBook_is_already_reserved_by_user() {
		return book_is_already_reserved_by_user;
	}

	public void setBook_is_already_reserved_by_user(boolean book_is_already_reserved_by_user) {
		this.book_is_already_reserved_by_user = book_is_already_reserved_by_user;
	}

	public Date getDate_when_book_is_back() {
		return date_when_book_is_back;
	}

	public void setDate_when_book_is_back(Date date_when_book_is_back) {
		this.date_when_book_is_back = date_when_book_is_back;
	}

	public String getWaiting_queue() {
		return waiting_queue;
	}

	public void setWaiting_queue(String waiting_queue) {
		this.waiting_queue = waiting_queue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNumberOfCopiesAvailable() {
		return numberOfCopiesAvailable;
	}

	public void setNumberOfCopiesAvailable(int numberOfCopiesAvailable) {
		this.numberOfCopiesAvailable = numberOfCopiesAvailable;
	}


}
