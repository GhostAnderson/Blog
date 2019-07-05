package com.laurence.blog.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author
{
	@Id
	@GeneratedValue
	private Integer pid;

	private String authorName;

	private String password;

	@OneToMany(mappedBy = "author")
	private List<Article> articleList;

	public Author()
	{
	}

	public Author(String authorName, String password, List<Article> articleList)
	{
		this.authorName = authorName;
		this.password = password;
		this.articleList = articleList;
	}

	public Integer getPid()
	{
		return pid;
	}

	public void setPid(Integer pid)
	{
		this.pid = pid;
	}

	public String getAuthorName()
	{
		return authorName;
	}

	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	public List<Article> getArticleList()
	{
		return articleList;
	}

	public void setArticleList(List<Article> articleList)
	{
		this.articleList = articleList;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
