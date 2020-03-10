package com.laurence.blog.Repository;

import com.laurence.blog.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer>
{
	Article findArticleByAid(Integer aid);

	@Query(nativeQuery = true,value = "SELECT * from article ORDER BY AID DESC LIMIT ?1,?2 ;")
	List<Article> findByPage(Integer start, Integer numPerPage);

	@Query(nativeQuery = true,value = "SELECT * from article where tid='2' ORDER BY AID DESC LIMIT ?1,?2 ;")
	List<Article> findByPhotograph(Integer start, Integer numPerPage);

	@Query(nativeQuery = true,value = "SELECT * from article where tid='1' ORDER BY AID DESC LIMIT ?1,?2 ;")
	List<Article> findByMindFuck(Integer start, Integer numPerPage);

	@Modifying
	@Transactional
	void deleteByAid(Integer aid);
}