package com.laurence.blog.Service;

import com.laurence.blog.Model.Tag;
import com.laurence.blog.Repository.ArticleRepository;
import com.laurence.blog.Repository.TagRepository;
import com.laurence.blog.Repository.UserRepository;
import com.laurence.blog.Repository.CommentsRepository;
import com.laurence.blog.Model.Article;
import com.laurence.blog.Utils.timeUtil;
import com.laurence.blog.Model.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class IndexServiceImplements implements IndexService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImplements.class);

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentsRepository commentsRepository;

	@Override
	public Article articles(Integer id)
	{
		Article article = articleRepository.findArticleByAid(id);
		return article;
	}

	@Override
	public List<Article> getPage(Integer page, Integer numperpage)
	{
		return articleRepository.findByPage(page,numperpage);
	}



	@Override
	public String Like(Integer aid)
	{
		Article article = articleRepository.findArticleByAid(aid);
		if(article == null)
		{
			return null;
		}
		else {
			article.setLikes(article.getLikes()+1);
			return "success";
		}
	}

	@Override
	public String Reply(Integer aid, String nickname, String content)
	{
		Article article = articleRepository.findArticleByAid(aid);
		if(article == null)
			return null;
		else
		{
			Comments comments = new Comments();
			comments.setArticle(article);
			comments.setAuthor(nickname);
			comments.setContent(content);

			if(nickname.length() <=0)
			{
				nickname = "匿名用户";
			}
			String avatar = Integer.toBinaryString(nickname.charAt(0));
			int a =Integer.parseInt(avatar,2);

			String ab = Integer.toString(a%10);


			avatar = "/img/avatar/"+ab+".jpg";

			comments.setAvatar(avatar);

			comments.setTime(timeUtil.getTimeString());

			commentsRepository.save(comments);
			return "success";
		}
	}

	@Override
	public List<Article> Gallery(Integer page, Integer numperpage)
	{
		return articleRepository.findByPhotograph(page,numperpage);
	}

	@Override
	public List<Article> getMindFuck(Integer page, Integer numperpage)
	{
		return articleRepository.findByMindFuck(page,numperpage);
	}
}
