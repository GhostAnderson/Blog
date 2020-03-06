package com.laurence.blog.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService
{

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
	{
		return null;
	}
}
