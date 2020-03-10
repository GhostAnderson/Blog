package com.laurence.blog.Controller;

import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.User;
import com.laurence.blog.Service.AdminService;
import com.laurence.blog.Service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController
{
	@Autowired
	IndexService indexService;

	@Autowired
	AdminService adminService;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	private static Integer numperpage = 6;

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
			temp = temp.replaceAll("<img src.*?>","");
			articles.get(i).setContent(temp.substring(0,temp.length()>150?150:temp.length()));
		}
		if(articles.size() == 0)
			return "404";
		model.addAttribute("articles",articles);

		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principle instanceof String)
			model.addAttribute("user","");
		else
			model.addAttribute("user",((User)principle).getUsername());
		return "index";
	}

	@GetMapping("/mindfuck")
	public String MindFuck(Model model,@RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else {
			page-=1;
		}

		List<Article> articles = indexService.getMindFuck(page*numperpage,numperpage);
		for (int i = 0;i<articles.size();i++)
		{
			String temp = articles.get(i).getContent();
			temp = temp.replaceAll("<img src.*?>","");
			articles.get(i).setContent(temp.substring(0,temp.length()>150?150:temp.length()));
		}
		model.addAttribute("articles",articles);
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principle instanceof String)
			model.addAttribute("user","");
		else
			model.addAttribute("user",((User)principle).getUsername());
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
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principle instanceof String)
			model.addAttribute("user","");
		else
			model.addAttribute("user",((User)principle).getUsername());
		return "article";
	}

	@GetMapping("/login")
	public String Login(HttpServletResponse response)
	{
		return "login";
	}

	@GetMapping("signup")
	public String Signup()
	{
		return "signup";
	}

	@PostMapping("signup")
	public String SignupHandle(@RequestParam("username") String username,
	                           @RequestParam("password") String password)
	{
		if(!indexService.Signup(username,password))
			return "redirect:signup?error";
		return "redirect:/";
	}

	@GetMapping("/admin/newarticle")
	public String newArticle()
	{
		return "upload";
	}

	@GetMapping("/admin")
	public String admin()
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
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principle instanceof String)
			model.addAttribute("user","");
		else
			model.addAttribute("user",((User)principle).getUsername());
		return "gallery";
	}

	@GetMapping("/admin/articlemanage")
	public String articleManage(Model model,@RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else
			page -=1;
		List<Article> articles = indexService.getPage(page*5,5);
		for (int i = 0;i<articles.size();i++)
		{
			String temp = articles.get(i).getContent();
			temp = temp.replaceAll("<img src.*?>","");
			articles.get(i).setContent(temp.substring(0,temp.length()>150?150:temp.length()));
		}

		model.addAttribute("articles",articles);
		model.addAttribute("page",page+1);
		model.addAttribute("totalpages",indexService.count()/5+1);
		return "articlemanage";
	}

	@GetMapping("/admin/usermanage")
	public String userManage(Model model,@RequestParam(value = "page",required = false)Integer page)
	{
		if (page == null)
			page = 0;
		else
			page -=1;
		model.addAttribute("users",adminService.findUserByPage(page*7,7));
		model.addAttribute("page",page+1);
		model.addAttribute("totalpages",adminService.userCount()/7+1);
		return "usermanage";
	}

}
