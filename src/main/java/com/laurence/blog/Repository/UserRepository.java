package com.laurence.blog.Repository;

import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>
{

	User findByUsername(String authorName);

	@Query(nativeQuery = true,value = "SELECT * from user ORDER BY pid DESC LIMIT ?1,?2 ;")
	List<User> findByPage(Integer start, Integer numPerPage);

	@Modifying
	@Transactional
	void deleteByUsername(String username);
}
