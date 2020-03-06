package com.deroussenicolas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


		@Autowired
		private MyUserDetailsService userDetailsService;
		
		@Autowired
		public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService);
			authProvider.setPasswordEncoder(encoder());
			return authProvider;
		}

		@Bean
		public PasswordEncoder encoder() {
			return new BCryptPasswordEncoder(11);
		}

		@Override
		public void configure(WebSecurity web) {
			web.ignoring().antMatchers("/resources/**", "/webjars/**", "/inscription");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/login").anonymous().anyRequest().authenticated()
			.and().formLogin().usernameParameter("email").passwordParameter("password").loginPage("/login").defaultSuccessUrl("/home", true).permitAll()
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.and().rememberMe().key("uniqueAndSecret");
		}
		
		  
	
}
