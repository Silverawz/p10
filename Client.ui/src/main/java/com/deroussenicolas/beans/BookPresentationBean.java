package com.deroussenicolas.beans;

import java.util.List;

public class BookPresentationBean {

	private int id_book;
	private String book_name;
	private String book_author;
	private String book_editor;
	private List <CopyBean> copy_list;
	private boolean is_reserved;
	private String date_when_book_is_back;
	private int waitingQueue;
	
	public BookPresentationBean() {
		super();
	}		

	public BookPresentationBean(int id_book, String book_name, String book_author, String book_editor, List<CopyBean> copy_list) {
		this.id_book = id_book;
		this.book_name = book_name;
		this.book_author = book_author;
		this.book_editor = book_editor;
		this.copy_list = copy_list;
	}

	public boolean isIs_reserved() {
		return is_reserved;
	}

	public void setIs_reserved(boolean is_reserved) {
		this.is_reserved = is_reserved;
	}

	public String getDate_when_book_is_back() {
		return date_when_book_is_back;
	}

	public void setDate_when_book_is_back(String date_when_book_is_back) {
		this.date_when_book_is_back = date_when_book_is_back;
	}

	public int getWaitingQueue() {
		return waitingQueue;
	}

	public void setWaitingQueue(int waitingQueue) {
		this.waitingQueue = waitingQueue;
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


}
