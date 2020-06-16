package com.deroussenicolas.batch;

import java.util.List;

import java.util.TimerTask;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.deroussenicolas.SmtpMailSender;
import com.deroussenicolas.beans.BookBean;
import com.deroussenicolas.beans.UserBean;
import com.deroussenicolas.proxies.MicroserviceBookProxy;
import com.deroussenicolas.proxies.MicroserviceUserProxy;


public class MyTask extends TimerTask {

	private SmtpMailSender smtpMailSender;	    
	private MicroserviceUserProxy microserviceUserProxy;
	private MicroserviceBookProxy microserviceBookProxy;
	final static Logger logger = LogManager.getLogger(MyTask.class);
	
	public MyTask(SmtpMailSender smtpMailSender, MicroserviceUserProxy microserviceUserProxy, MicroserviceBookProxy microserviceBookProxy) {
		this.microserviceUserProxy = microserviceUserProxy;
		this.smtpMailSender = smtpMailSender;
		this.microserviceBookProxy = microserviceBookProxy;
	}
	
	
	
	@Override
	public void run() {
		List<UserBean> userBeanListToSendEmail = microserviceUserProxy.usersListToSendEmail();
		List<BookBean> bookBeanList = microserviceBookProxy.listOfAllBooksToSendEmail();	
		int incrementation = 0;
		for (UserBean userBean : userBeanListToSendEmail) {			
			try {
				smtpMailSender.send(userBean.getEmail(), "Your reservation has expired", 
				"Hello, your reservation for the book : " + bookBeanList.get(incrementation).getBook_name() + " has expired. "
			    + "Please bring the book back to the library as soon as possible. cheer. "); 
				logger.info("confirmed email send to = " + userBean.getEmail() + " AND for book = " + bookBeanList.get(incrementation).getBook_name());
				incrementation++;
			}
			catch (MessagingException e) {
				logger.error("Error for user = " + userBean.getEmail() + " AND for book = " + bookBeanList.get(incrementation).getBook_name());
				e.printStackTrace(); 
			}			  
		}
	}

}
