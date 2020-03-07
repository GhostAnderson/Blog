package com.laurence.blog.Repository;

import com.laurence.blog.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer>
{
	Role findByRoleName(String rolename);
}
