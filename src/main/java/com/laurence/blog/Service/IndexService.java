package com.laurence.blog.Service;

import com.laurence.blog.Model.Article;

import java.util.List;

public interface IndexService
{
	Article articles(Integer id);

	List<Article> getPage(Integer page, Integer numperpage);

	String Like(Integer aid);

	String Reply(Integer aid, String nickname, String content);

	List<Article> Gallery(Integer page, Integer numperpage);

	List<Article> getMindFuck(Integer page,Integer numperpage);

	Long count();

	boolean Signup(String username, String password);
}
