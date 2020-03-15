package com.laurence.blog.Repository;

import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer>
{
	@Query(nativeQuery = true,value = "SELECT * from comments ORDER BY CID DESC LIMIT ?1,?2 ;")
	List<Comments> findByPage(Integer start, Integer numPerPage);

}
