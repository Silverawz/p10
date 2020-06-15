package com.deroussenicolas;

import java.util.Calendar;


import java.util.Timer;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.deroussenicolas.batch.MyTask;
import com.deroussenicolas.proxies.MicroserviceBookProxy;
import com.deroussenicolas.proxies.MicroserviceUserProxy;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@SpringBootApplication
@EnableFeignClients("com.deroussenicolas.proxies")
@EnableWebSecurity
public class Application extends SpringBootServletInitializer implements CommandLineRunner  {
	
	@Autowired
	private SmtpMailSender smtpMailSender;	
	@Autowired   
	private MicroserviceUserProxy microserviceUserProxy;
	@Autowired   
	private MicroserviceBookProxy microserviceBookProxy;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*
		 * Set the time for the batch, here is 2 AM
		 */
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		/*
		 * Email batch
		 * Sending a mail to every user who didnt return back their book according to the reservation date
		 * Every night at 2 AM, the task will run
		 */
		/*
		Timer timer = new Timer(); 
		timer.schedule(
				new MyTask(smtpMailSender,microserviceUserProxy, microserviceBookProxy), 
				today.getTime(), 
				TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS)
		); */
		// period: 1 day DAYS 
	}

}
