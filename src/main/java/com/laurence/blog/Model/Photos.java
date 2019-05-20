package com.laurence.blog.Model;

import javax.persistence.*;

@Entity
public class Photos
{
	@Id
	@GeneratedValue
	private Integer phid;

	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aid")
	private Article article;

	public Integer getPhid()
	{
		return phid;
	}

	public void setPhid(Integer phid)
	{
		this.phid = phid;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Article getArticle()
	{
		return article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}
}
