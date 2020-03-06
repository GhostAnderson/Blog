package com.laurence.blog.Repository;

import com.laurence.blog.Model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Integer>
{
}
