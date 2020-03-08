package com.laurence.blog.Service;

import com.laurence.blog.Model.Role;
import com.laurence.blog.Model.User;
import com.laurence.blog.Repository.ArticleRepository;
import com.laurence.blog.Repository.RoleRepository;
import com.laurence.blog.Repository.UserRepository;
import com.laurence.blog.Repository.CommentsRepository;
import com.laurence.blog.Model.Article;
import com.laurence.blog.Utils.timeUtil;
import com.laurence.blog.Model.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IndexServiceImplements implements IndexService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImplements.class);

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentsRepository commentsRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

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
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(principal instanceof String)
			{
				if(nickname.length()<=0)
					nickname = "匿名用户";
			}
			else {
				nickname = ((User)principal).getUsername();
			}
			Comments comments = new Comments();
			comments.setArticle(article);
			comments.setAuthor(nickname);
			comments.setContent(content);


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

	@Override
	public boolean Signup(String username, String password)
	{
		User user = userRepository.findByUsername(username);
		if (user!= null)
			return false;
		assert user == null;
		user = new User();
		user.setUsername(username);
		List<Role> roles = new ArrayList<>();
		Role normal = roleRepository.findByRoleName("user");
		roles.add(normal);
		user.setRoleList(roles);

		user.setPassword(passwordEncoder.encode(password));

		userRepository.save(user);
		return true;
	}
}
