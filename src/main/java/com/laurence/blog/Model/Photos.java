package com.laurence.blog.Model;

import javax.persistence.*;

@Entity
public class Photos
{
	@Id
	@GeneratedValue
	private Integer phid;

	private String path;

	public Photos(String path, String thumb_path, Article article) {
		this.path = path;
		this.thumb_path = thumb_path;
		this.article = article;
	}

	public Photos() {
	}

	public String getThumb_path() {
		return thumb_path;
	}

	public void setThumb_path(String thumb_path) {
		this.thumb_path = thumb_path;
	}

	private String thumb_path;

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
