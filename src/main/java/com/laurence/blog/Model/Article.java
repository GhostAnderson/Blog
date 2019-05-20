package com.laurence.blog.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article
{
	@Id
	@GeneratedValue
	private Integer aid;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pid")
	private Author author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tid")
	private Tag tag;

	private String title;

	private String content;

	private String coverImage;

	private String time;

	private Integer likes;

	@OneToMany(mappedBy = "article")
	private List<Comments>commentsList;

	@OneToMany(mappedBy = "article")
	private List<Photos> photosList;

	public Article()
	{
	}

	public Article(Author author, Tag tag, String title, String content, String coverImage, String time, Integer likes, List<Comments> commentsList)
	{
		this.author = author;
		this.tag = tag;
		this.title = title;
		this.content = content;
		this.coverImage = coverImage;
		this.time = time;
		this.likes = likes;
		this.commentsList = commentsList;
	}

	public Article(Author author, Tag tag, String title, String content, String coverImage, String time, Integer likes, List<Comments> commentsList, List<Photos> photosList)
	{
		this.author = author;
		this.tag = tag;
		this.title = title;
		this.content = content;
		this.coverImage = coverImage;
		this.time = time;
		this.likes = likes;
		this.commentsList = commentsList;
		this.photosList = photosList;
	}

	public Integer getAid()
	{
		return aid;
	}

	public void setAid(Integer aid)
	{
		this.aid = aid;
	}

	public Author getAuthor()
	{
		return author;
	}

	public void setAuthor(Author author)
	{
		this.author = author;
	}

	public Tag getTag()
	{
		return tag;
	}

	public void setTag(Tag tag)
	{
		this.tag = tag;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public List<Comments> getCommentsList()
	{
		return commentsList;
	}

	public void setCommentsList(List<Comments> commentsList)
	{
		this.commentsList = commentsList;
	}

	public String getCoverImage()
	{
		return coverImage;
	}

	public void setCoverImage(String coverImage)
	{
		this.coverImage = coverImage;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public Integer getLikes()
	{
		return likes;
	}

	public void setLikes(Integer likes)
	{
		this.likes = likes;
	}

	public List<Photos> getPhotosList()
	{
		return photosList;
	}

	public void setPhotosList(List<Photos> photosList)
	{
		this.photosList = photosList;
	}
}
