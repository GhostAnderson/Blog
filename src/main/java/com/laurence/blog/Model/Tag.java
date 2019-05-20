package com.laurence.blog.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Tag
{
	@Id
	@GeneratedValue
	private Integer tid;


	private String tagName;

	@OneToMany(mappedBy = "tag")
	private List<Article>articleList;

	public Tag()
	{
	}

	public Tag(String tagName, List<Article> articleList)
	{
		this.tagName = tagName;
		this.articleList = articleList;
	}

	public Integer getTid()
	{
		return tid;
	}

	public void setTid(Integer tid)
	{
		this.tid = tid;
	}

	public String getTagName()
	{
		return tagName;
	}

	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}

	public List<Article> getArticleList()
	{
		return articleList;
	}

	public void setArticleList(List<Article> articleList)
	{
		this.articleList = articleList;
	}
}
