package com.laurence.blog.Controller;

import com.laurence.blog.DAO.AuthorDAO;
import com.laurence.blog.Model.Author;
import com.laurence.blog.Service.AdminService;
import com.laurence.blog.Service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AdminController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@Autowired
	IndexService indexService;

	@PostMapping("/admin/submit")
	public String SubmitArticle(@RequestParam("title")String title,
	                            @RequestParam("cover")MultipartFile coverpic,
	                            @RequestParam("tag")Integer tid,
	                            @RequestParam("content")String content)
	{
		return adminService.SubmitArticle(title, coverpic, tid, content);
	}

	@PostMapping("/admin/login")
	public String AdminLogin(@RequestParam("username") String username,
							 @RequestParam("pass") String pass,
							 HttpServletRequest request)
	{

		return adminService.AdminLogin(username,pass,request);
	}



	@PostMapping("/archives/reply")
	public String reply(@RequestParam("aid") Integer aid,
	                    @RequestParam("nickname") String nickname,
	                    @RequestParam("content") String content)
	{
		return indexService.Reply(aid,nickname,content);
	}


	@PostMapping("/archives/like")
	public String Like(@RequestParam("aid") Integer aid)
	{
		return indexService.Like(aid);
	}

	@PostMapping("/archives/uploadpics")
	public String uploadPics(@RequestParam("aid") Integer aid,MultipartFile file)
	{
		return adminService.uploadPics(aid,file);
	}
}
