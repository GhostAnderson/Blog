package com.laurence.blog.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface AdminService
{
	String SubmitArticle(String title, MultipartFile cover,Integer tid, String content);

	String uploadPics(Integer aid, MultipartFile file);

	String AdminLogin(String username, String pass, HttpServletRequest request);
}
