package com.laurence.blog.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Role
{
	@GeneratedValue
	@Id
	Integer rid;

	String roleName;
	String Description;

	@ManyToMany(mappedBy = "roleList")
	List<User> userList;
}
