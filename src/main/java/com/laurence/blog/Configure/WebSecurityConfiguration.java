package com.laurence.blog.Configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/archives/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/css/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.and()
				.logout()
				.deleteCookies("JSESSION")
				.logoutSuccessUrl("/");
	}

}