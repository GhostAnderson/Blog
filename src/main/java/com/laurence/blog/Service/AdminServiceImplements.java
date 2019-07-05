package com.laurence.blog.Service;

import com.laurence.blog.DAO.ArticleDAO;
import com.laurence.blog.DAO.AuthorDAO;
import com.laurence.blog.DAO.PhotoDAO;
import com.laurence.blog.DAO.TagDAO;
import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.Author;
import com.laurence.blog.Model.Photos;
import com.laurence.blog.Model.Tag;
import com.laurence.blog.Utils.ThumbsUtil;
import com.laurence.blog.Utils.timeUtil;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Service
public class AdminServiceImplements implements AdminService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImplements.class);

	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	TagDAO tagDAO;

	@Autowired
	PhotoDAO photoDAO;

	@Autowired
	AuthorDAO authorDAO;



	@Override
	public String SubmitArticle(String title, MultipartFile cover, Integer tid, String content)
	{
		try
		{

			Tag tag = tagDAO.findByTid(tid);
			if (tag == null)
				return null;
			Article article = new Article(null, tag, title, content, null, null, 0, null);
			articleDAO.save(article);

			Integer aid = article.getAid();
			String name = aid + "-cover.jpg";
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if(!path.exists())
			{
				path=new File("");
			}
			String uploadpath = path.getAbsolutePath()+"/static/img/upload";
			uploadFile(cover.getBytes(),uploadpath,name);
			String filePath = "/img/upload/"+name;
			String thumbPath = "/img/upload/thumb-"+name;

			String time = timeUtil.getDateString();
			article.setTime(time);

			article.setCoverImage(filePath);
			article.setThumbImage(thumbPath);
			articleDAO.save(article);
			return "success";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.toString();
		}

	}

	@Override
	public String uploadPics(Integer aid, MultipartFile file)
	{
		Article article = articleDAO.findArticleByAid(aid);
		if(article == null)
		{
			return null;
		}
		else if (article.getTag().getTid() != 2)
		{
			return null;
		}
		else
		{
			Photos photo = new Photos();
			photo.setArticle(article);
			photoDAO.save(photo);


			String name = article.getPhotosList().size()+"-"+article.getAid()+".jpg";
			File path = null;
			try
			{
				path = new File(ResourceUtils.getURL("classpath:").getPath());
			} catch (FileNotFoundException e)
			{
				return null;
			}
			if(!path.exists())
			{
				path=new File("");
			}
			String uploadpath = path.getAbsolutePath()+"/static/img/upload";
			try
			{
				uploadFile(file.getBytes(),uploadpath,name);
			} catch (Exception e)
			{
				return null;
			}
			String filePath = "/img/upload/"+name;
			String thumbPath = "/img/upload/thumb-"+name;
			photo.setPath(filePath);
			photo.setThumb_path(thumbPath);
			photoDAO.save(photo);
			return "success";
		}
	}

	@Override
	public String AdminLogin(String username, String pass, HttpServletRequest request) {
		if(hasLoggedIn(username,pass))
		{
			request.getSession().setAttribute("Author",authorDAO.findByAuthorName(username));
			return "/admin";
		}
		return "/login";
	}

	private static void uploadFile(byte[] file,String filePath,String fileName) throws Exception
	{
		File targetPath = new File(filePath);
		filePath = targetPath.getAbsolutePath();
		if(!targetPath.exists())
		{
			targetPath.mkdir();
		}
		FileOutputStream out= new FileOutputStream(filePath+"/"+fileName);


		out.write(file);
		out.flush();
		out.close();

		Thumbnails.of(filePath+"/"+fileName).size(1024,768).toFile(filePath+"/thumb-"+fileName);
	}

	private Boolean hasLoggedIn(String username, String pass)
	{
		Author author = authorDAO.findByAuthorName(username);
		if(author == null)
		{
			return false;
		}
		else
			return author.getPassword().equals(pass);
	}
}
