package com.laurence.blog.Service;

import com.laurence.blog.Model.Comments;
import com.laurence.blog.Model.User;
import com.laurence.blog.Utils.CustomResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService
{
	String SubmitArticle(String title, MultipartFile cover, Integer tid, String content);

	String uploadPics(Integer aid, MultipartFile file);

	CustomResponse upload(MultipartFile file);

	CustomResponse delete(Integer aid);

	List<User> findUserByPage(Integer start, Integer numperpage);

	Long userCount();

	CustomResponse deleteUser(String username);

	CustomResponse privmanage(String username, List<String> rolelist);

	CustomResponse banuser(String username);

	CustomResponse unbanuser(String username);

	List<Comments> getComments(Integer page, Integer numperpage);

	Long commentCount();

	CustomResponse deleteComment(Integer cid);
}
