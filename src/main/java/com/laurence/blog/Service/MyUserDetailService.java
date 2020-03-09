package com.laurence.blog.Service;

import com.laurence.blog.Model.Role;
import com.laurence.blog.Repository.RoleRepository;
import com.laurence.blog.Repository.UserRepository;
import com.laurence.blog.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class MyUserDetailService implements UserDetailsService
{
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(s);
		if(null == user)
			throw new UsernameNotFoundException("User "+s+" not found");
		return user;
	}
}
