package com.laurence.blog.Repository;

import com.laurence.blog.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>
{

	User findByUsername(String authorName);
}
