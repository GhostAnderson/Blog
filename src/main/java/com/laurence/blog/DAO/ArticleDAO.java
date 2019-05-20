package com.laurence.blog.DAO;

import com.laurence.blog.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleDAO extends JpaRepository<Article,Integer>
{
	Article findArticleByAid(Integer aid);

	@Query(nativeQuery = true,value = "SELECT * from article LIMIT ?1,?2")
	List<Article> findByPage(Integer start, Integer numPerPage);

	@Query(nativeQuery = true,value = "SELECT * from article where tid='2' LIMIT ?1,?2 ;")
	List<Article> findByPhotograph(Integer start, Integer numPerPage);

	@Query(nativeQuery = true,value = "SELECT * from article where tid='1' LIMIT ?1,?2 ;")
	List<Article> findByMindFuck(Integer start, Integer numPerPage);
}
