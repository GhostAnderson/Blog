package com.laurence.blog.Model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User implements UserDetails
{
	@Id
	@GeneratedValue
	private Integer pid;

	private String username;

	private String password;


	@OneToMany(mappedBy = "user")
	private List<Article> articleList;

	@ManyToMany(fetch=FetchType.EAGER)
	private List<Role> roleList;

	public User()
	{
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		for(Role role: roleList){
			grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		}
		return grantedAuthorityList;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
