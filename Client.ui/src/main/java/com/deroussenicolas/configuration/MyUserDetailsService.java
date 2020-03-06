package com.deroussenicolas.configuration;

import java.io.Serializable;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deroussenicolas.beans.UserBean;
import com.deroussenicolas.proxies.MicroserviceUserProxy;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	final static Logger logger = LogManager.getLogger(MyUserDetailsService.class);
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//	HttpServletRequest request = ((ServletRequestAttributes ) RequestContextHolder.getRequestAttributes()).getRequest();
	//	String email = request.getParameter("email");
		UserBean userBean = microserviceUserProxy.loadUserByUsername(username);
		MyUserDetails myUserDetails = new MyUserDetails(userBean);
		if(myUserDetails != null) {
			return myUserDetails;
		}
		throw new UsernameNotFoundException("User not authorized.");	
	}

}
