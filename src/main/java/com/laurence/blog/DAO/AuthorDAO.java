package com.laurence.blog.DAO;

import com.laurence.blog.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDAO extends JpaRepository<Author,Integer>
{

	Author findByAuthorName(String authorName);
}
