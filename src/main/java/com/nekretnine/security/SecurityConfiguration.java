package com.nekretnine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		
		authenticationManagerBuilder
				.userDetailsService(this.userDetailsService).passwordEncoder(
						passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()


				.antMatchers( "/api/users/login", "/api/users/register/**","/api/users/verify/**").
					permitAll()
					.and()
				.authorizeRequests()
				.antMatchers( "/api/advertiser/profile/","/api/customer/profile/","api/account/addMoney/**","api/account/config","api/account/get"). 
				hasAuthority("CUSTOMER|ADVERTISER")			
				.and()
			.authorizeRequests()
				.antMatchers( "/api/administrator/register")
				.hasAuthority("ADMINISTRATOR")
				.and()
				.authorizeRequests()
				.antMatchers("api/advertiser/callToCompany","api/advertiser/acceptCall",
						"api/advertiser/allCalls","api/advertiser/myprofile","/api/advertiser/unemployed","/api/advertiser/soldEstates")
				.hasAuthority("ADVERTISER")				
				.and()
				.authorizeRequests()
				.antMatchers("/api/customer/myprofile","/api/customer/myEstates")
				.hasAuthority("CUSTOMER")
				.and()
			.authorizeRequests()
				.antMatchers( "/api/administrator/register")
				.hasAuthority("ADMINISTRATOR")
				.anyRequest().authenticated();
				
		
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(),
				UsernamePasswordAuthenticationFilter.class);
	} 
	
	

}
