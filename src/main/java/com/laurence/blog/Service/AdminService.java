package com.laurence.blog.Service;

import org.springframework.web.multipart.MultipartFile;

public interface AdminService
{
	String SubmitArticle(String title, MultipartFile cover,Integer tid, String content);

	String uploadPics(Integer aid, MultipartFile file);
}
