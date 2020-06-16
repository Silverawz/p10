package com.deroussen.batch;

import java.util.TimerTask;

import com.deroussenicolas.SmtpMailSender;
import com.deroussenicolas.service.BookService;


public class MyTask extends TimerTask {
	 
	private BookService bookService;
	
	
	public MyTask(BookService bookService) {
		this.bookService = bookService;
	}
	
	@Override
	public void run() {
		System.err.println("batch en cours!");
		bookService.batchBook();
	}

}
