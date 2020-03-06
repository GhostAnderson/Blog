package com.laurence.blog.Service;

import com.laurence.blog.Model.User;
import com.laurence.blog.Repository.ArticleRepository;
import com.laurence.blog.Repository.UserRepository;
import com.laurence.blog.Repository.PhotoRepository;
import com.laurence.blog.Repository.TagRepository;
import com.laurence.blog.Model.Article;
import com.laurence.blog.Model.Photos;
import com.laurence.blog.Model.Tag;
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


	private static String uploadFile(byte[] file,String filePath,String fileName) throws IOException
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

}
