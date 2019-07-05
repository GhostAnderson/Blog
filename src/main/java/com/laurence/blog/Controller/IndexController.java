package com.laurence.blog.Controller;

import com.laurence.blog.Model.Article;
import com.laurence.blog.Service.IndexService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController
{
	@Autowired
	IndexService indexService;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	static Integer numperpage = 6;

	private final static String cookieName = "fldsakjfl";
	private final static String cookieContent = "kjuhdsfi65786198phlklmfjgsadyf987420395u";


	@GetMapping("/")
	public String Index(Model model, @RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else if (page <=0)
		{
			return "404";
		}
		else {
			page-=1;
		}

		List<Article> articles = indexService.getPage(page*numperpage,numperpage);
		for (int i = 0;i<articles.size();i++)
		{
			String temp = articles.get(i).getContent();
			articles.get(i).setContent(temp.substring(0,temp.length()>150?150:temp.length()));
		}
		if(articles.size() == 0)
			return "404";
		model.addAttribute("articles",articles);
		return "index";
	}

	@GetMapping("/mindfuck")
	public String MindFuck(Model model,@RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else if (page <=0)
		{
			return "404";
		}
		else {
			page-=1;
		}

		List<Article> articles = indexService.getMindFuck(page*numperpage,numperpage);
		if(articles.size() == 0)
			return "404";
		for (int i = 0;i<articles.size();i++)
		{
			String temp = articles.get(i).getContent();
			articles.get(i).setContent(temp.substring(0,temp.length()>150?150:temp.length()));
		}
		model.addAttribute("articles",articles);
		return "mindfuck";
	}

	@GetMapping("/archives/{id}")
	public String Articles(Model model, @PathVariable("id") Integer id,HttpServletResponse response){

		Article article = indexService.articles(id);
		if (article == null)
		{
			return "404";
		}
		model.addAttribute("article",article);
		return "article";
	}

	@GetMapping("/login")
	public String Login(HttpServletResponse response)
	{
		return "login";
	}

	@GetMapping("/admin")
	public String Admin(HttpServletRequest request)
	{
		return "admin";
	}

	@GetMapping("/gallery")
	public String Gallery(Model model,@RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else if (page <=0)
		{
			return "404";
		}
		else {
			page-=1;
		}

		List<Article> articleList = indexService.Gallery(page*numperpage,numperpage);
		model.addAttribute("articles",articleList);
		return "gallery";
	}
}
