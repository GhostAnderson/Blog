package com.laurence.blog.Service;

import com.laurence.blog.Model.*;
import com.laurence.blog.Repository.*;
import com.laurence.blog.Utils.CustomResponse;
import com.laurence.blog.Utils.RandomStringUtil;
import com.laurence.blog.Utils.timeUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImplements implements AdminService
{

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	PhotoRepository photoDAO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CommentsRepository commentsRepository;

	@Override
	public String SubmitArticle(String title, MultipartFile cover, Integer tid, String content)
	{
		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			assert !principal.equals("anonymous");
			User user = (User)principal;

			Tag tag = tagRepository.findByTid(tid);
			if (tag == null)
				return null;
			Article article = new Article(user, tag, title, content, null, null, 0, null);
			articleRepository.save(article);

			Integer aid = article.getAid();
			String name = aid + "-cover.jpg";
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if(!path.exists())
			{
				path=new File("");
			}
			String uploadpath = path.getAbsolutePath()+"/static/img/upload";
			String truePath = uploadFile(cover.getBytes(),uploadpath,name);
			Thumbnails.of(truePath).size(1280, 720).toFile(path.getAbsolutePath()+"/static/img/upload/thumb-" + name);

			String filePath = "/img/upload/"+name;
			String thumbPath = "/img/upload/thumb-"+name;

			String time = timeUtil.getDateString();
			article.setTime(time);

			article.setCoverImage(filePath);
			article.setThumbImage(thumbPath);
			articleRepository.save(article);
			return "success";
		}
		catch (AssertionError e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return null;
		}
		catch (Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String uploadPics(Integer aid, MultipartFile file)
	{
		try
		{

			Article article = articleRepository.findArticleByAid(aid);
			if (article == null)
			{
				return null;
			} else if (article.getTag().getTid() != 2)
			{
				return null;
			} else
			{
				Photos photo = new Photos();
				photo.setArticle(article);
				photoDAO.save(photo);


				String name = article.getPhotosList().size() + "-" + article.getAid() + ".jpg";
				File path = null;
				try
				{
					path = new File(ResourceUtils.getURL("classpath:").getPath());
				} catch (FileNotFoundException e)
				{
					return null;
				}
				if (!path.exists())
				{
					path = new File("");
				}
				String uploadpath = path.getAbsolutePath() + "/static/img/upload";
				String truePath =  uploadFile(file.getBytes(), uploadpath, name);
				String filePath = "/img/upload/" + name;
				String thumbPath = "/img/upload/thumb-" + name;

				Thumbnails.of(truePath).size(1280, 720).toFile(path.getAbsolutePath()+"/static/img/upload/thumb-" + name);
				photo.setPath(filePath);
				photo.setThumb_path(thumbPath);
				photoDAO.save(photo);
				return "success";
			}

		}catch (IOException e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CustomResponse upload(MultipartFile file)
	{
		try
		{
			String name = RandomStringUtil.getRandomString(10)+".jpg";
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if(!path.exists())
			{
				path=new File("");
			}
			String uploadpath = path.getAbsolutePath()+"/static/img/upload";
			String truePath = uploadFile(file.getBytes(),uploadpath,name);

			String filePath = "/img/upload/"+name;

			List<String> rtn = new ArrayList<>();
			rtn.add(filePath);
			return CustomResponse.createResponse("0",rtn);

		} catch (IOException e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return CustomResponse.createResponse("1",e.toString());
		}
	}

	@Override
	public CustomResponse delete(Integer aid)
	{
		try
		{
			articleRepository.deleteByAid(aid);
			return CustomResponse.createResponse("200",null);
		}
		catch (Exception e)
		{
			return CustomResponse.createResponse("500",null);
		}
	}

	@Override
	public List<User> findUserByPage(Integer start, Integer numperpage)
	{
		return userRepository.findByPage(start,numperpage);
	}

	@Override
	public Long userCount()
	{
		return userRepository.count();
	}

	@Override
	public CustomResponse deleteUser(String username)
	{
		try
		{
			userRepository.deleteByUsername(username);
			return CustomResponse.createResponse("200", "success");
		} catch (Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return CustomResponse.createResponse("500",e.toString());
		}
	}

	@Override
	public CustomResponse privmanage(String username, List<String> rolelist)
	{
		try
		{
			User user = userRepository.findByUsername(username);
			List<Role> roles = new ArrayList<>();

			for (String role : rolelist)
			{
				role = role.substring(5);
				Role r = roleRepository.findByRoleName(role);
				roles.add(r);
			}
			user.setRoleList(roles);
			userRepository.save(user);
			return CustomResponse.createResponse("200","success");
		}
		catch (Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return CustomResponse.createResponse("500","error");
		}
	}

	@Override
	public CustomResponse banuser(String username)
	{
		try
		{
			User user = userRepository.findByUsername(username);
			user.setIsEnable(false);
			userRepository.save(user);
			return CustomResponse.createResponse("200","success");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.toString());
			return CustomResponse.createResponse("500",e.toString());
		}
	}

	@Override
	public CustomResponse unbanuser(String username)
	{
		try
		{
			User user = userRepository.findByUsername(username);
			user.setIsEnable(true);
			userRepository.save(user);
			return CustomResponse.createResponse("200","success");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error(e.toString());
			return CustomResponse.createResponse("500",e.toString());
		}
	}

	@Override
	public List<Comments> getComments(Integer page, Integer numperpage)
	{
		return commentsRepository.findByPage(page,numperpage);
	}

	@Override
	public Long commentCount()
	{
		return commentsRepository.count();
	}

	private static String uploadFile(byte[] file, String filePath, String fileName) throws IOException
	{
		File targetPath = new File(filePath);
		filePath = targetPath.getAbsolutePath();
		if(!targetPath.exists())
		{
			targetPath.mkdirs();
		}
		FileOutputStream out= new FileOutputStream(filePath+"/"+fileName);
		out.write(file);
		out.flush();
		out.close();
		return filePath+"/"+fileName;
	}

	@Override
	public CustomResponse deleteComment(Integer cid)
	{
		try
		{
			commentsRepository.deleteById(cid);
			return CustomResponse.createResponse("200","success");
		}
		catch (Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
			return CustomResponse.createResponse("500",e.toString());
		}
	}
}
