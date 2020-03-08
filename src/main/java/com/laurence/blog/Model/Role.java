package com.laurence.blog.Model;

import lombok.Data;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.awt.*;
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

	@ManyToMany(mappedBy = "roleList",fetch = FetchType.LAZY)
	List<User> userList;
}
