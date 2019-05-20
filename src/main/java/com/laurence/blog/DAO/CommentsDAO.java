package com.laurence.blog.DAO;

import com.laurence.blog.Model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsDAO extends JpaRepository<Comments,Integer>
{
}
