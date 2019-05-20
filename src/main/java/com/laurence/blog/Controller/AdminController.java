package com.laurence.blog.Controller;

import com.laurence.blog.DAO.AuthorDAO;
import com.laurence.blog.Model.Author;
import com.laurence.blog.Service.AdminService;
import com.laurence.blog.Service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AdminController
{
	@Autowired
	AdminService adminService;

	@Autowired
	IndexService indexService;

	private final static String cookieName = "fldsakjfl";
	private final static String cookieContent = "kjuhdsfi65786198phlklmfjgsadyf987420395u";

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
	                         HttpServletResponse response)
	{
		if(indexService.hasLoggedIn(username,pass))
		{
			response.addCookie(new Cookie(cookieName,cookieContent));
			return "/admin";
		}
		return null;
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
