package com.laurence.blog.Service;

import com.laurence.blog.DAO.ArticleDAO;
import com.laurence.blog.DAO.PhotoDAO;
import com.laurence.blog.DAO.TagDAO;
import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.Photos;
import com.laurence.blog.Model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

@Service
public class AdminServiceImplements implements AdminService
{
	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	TagDAO tagDAO;

	@Autowired
	PhotoDAO photoDAO;

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
			article.setCoverImage(filePath);
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
			photo.setPath(filePath);
			photoDAO.save(photo);
			return "success";
		}
	}


	public static void uploadFile(byte[] file,String filePath,String fileName) throws Exception
	{
		File targetFile = new File(filePath);
		filePath = targetFile.getAbsolutePath();
		if(!targetFile.exists())
		{
			targetFile.mkdir();
		}
		FileOutputStream out= new FileOutputStream(filePath+"/"+fileName);
		out.write(file);
		out.flush();
		out.close();
	}
}
