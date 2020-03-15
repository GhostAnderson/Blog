package com.laurence.blog.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Article
{
	@Id
	@GeneratedValue
	private Integer aid;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pid")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tid")
	private Tag tag;

	private String title;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String content;

	private String coverImage;

	public String getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(String thumbImage) {
		this.thumbImage = thumbImage;
	}

	private String thumbImage;

	private String time;

	private Integer likes;

	private Integer viewTime = 0;

	@OneToMany(mappedBy = "article",orphanRemoval = true,cascade = CascadeType.REMOVE)
	private List<Comments>commentsList;

	@OneToMany(mappedBy = "article",orphanRemoval = true,cascade = CascadeType.REMOVE)
	private List<Photos> photosList;

	public Article()
	{
	}

	public Article(User user, Tag tag, String title, String content, String coverImage, String time, Integer likes, List<Comments> commentsList)
	{
		this.user = user;
		this.tag = tag;
		this.title = title;
		this.content = content;
		this.coverImage = coverImage;
		this.time = time;
		this.likes = likes;
		this.commentsList = commentsList;
	}

}
