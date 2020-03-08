package com.laurence.blog.Service;

import com.laurence.blog.Utils.CustomResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface AdminService
{
	String SubmitArticle(String title, MultipartFile cover,Integer tid, String content);

	String uploadPics(Integer aid, MultipartFile file);

	CustomResponse upload(MultipartFile file);
}
