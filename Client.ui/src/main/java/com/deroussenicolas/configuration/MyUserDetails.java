package com.deroussenicolas.configuration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.deroussenicolas.beans.UserBean;

@Component
public class MyUserDetails implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	final static Logger logger = LogManager.getLogger(MyUserDetails.class);
	private UserBean user;

    public MyUserDetails(UserBean user) {
    	logger.info("MyUserDetails, constructor with user as parameter" );
        this.user = user;
    }
    
    public MyUserDetails() {
    	logger.info("MyUserDetails, without parameter" );
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("User"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
