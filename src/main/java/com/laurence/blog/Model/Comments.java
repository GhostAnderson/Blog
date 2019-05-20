package com.laurence.blog.Model;

import javax.persistence.*;

@Entity
public class Comments
{
	@Id
	@GeneratedValue
	private Integer cid;

	private String author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="aid")
	private Article article;

	private String time;

	private String content;

	private String avatar;

	public Comments(String author, Article article, String time, String content, String avatar)
	{
		this.author = author;
		this.article = article;
		this.time = time;
		this.content = content;
		this.avatar = avatar;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}


	public Comments()
	{
	}

	public Integer getCid()
	{
		return cid;
	}

	public void setCid(Integer cid)
	{
		this.cid = cid;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public Article getArticle()
	{
		return article;
	}

	public void setArticle(Article article)
	{
		this.article = article;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}
}
