package com.laurence.blog.Controller;

import com.laurence.blog.Service.AdminService;
import com.laurence.blog.Service.IndexService;
import com.laurence.blog.Utils.CustomResponse;
import com.sun.org.apache.xpath.internal.operations.Mult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Slf4j
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


	@PostMapping("/archives/reply")
	public String reply(@RequestParam("aid") Integer aid,
	                    @RequestParam(name = "nickname",required = false) String nickname,
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


	@PostMapping("/admin/upload")
	public CustomResponse uploadPics(MultipartFile file)
	{
		return adminService.upload(file);
	}

	@PostMapping("/admin/delete")
	public CustomResponse delete(@RequestParam("aid")Integer aid)
	{
		return adminService.delete(aid);
	}


	@PostMapping("/admin/privmanage")
	public CustomResponse privilageManage(@RequestParam("username")String username,
	                                      @RequestParam("rolelist")List<String> rolelist)
	{
		log.info("try to set user "+ username+"'s privilage as "+String.join(",",rolelist));
		return adminService.privmanage(username,rolelist);
	}

	@PostMapping("/admin/deleteuser")
	public CustomResponse deleteUser(@RequestParam("username")String username)
	{
		log.info("try to delete user "+username);
		return adminService.deleteUser(username);
	}

	@PostMapping("/admin/banuser")
	public CustomResponse banUser(@RequestParam("username")String username)
	{
		log.info("try to ban user "+username);
		return adminService.banuser(username);
	}

	@PostMapping("/admin/unbanuser")
	public CustomResponse unbanUser(@RequestParam("username")String username)
	{
		log.info("try to unban user "+username);
		return adminService.unbanuser(username);
	}

	@PostMapping("/admin/deletecomment")
	public CustomResponse deleteComment(@RequestParam("cid")Integer cid)
	{
		log.info("try to delete comment "+cid);
		return adminService.deleteComment(cid);
	}
}
