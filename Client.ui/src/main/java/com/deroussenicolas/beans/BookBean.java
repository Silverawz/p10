package com.deroussenicolas.beans;

import java.io.Serializable;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



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

	@Override
	public String toString() {
		return "BookBean [id_book=" + id_book + ", book_name=" + book_name + ", copy_list=" + copy_list + "]";
	}


}
