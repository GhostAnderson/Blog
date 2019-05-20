package com.laurence.blog.DAO;

import com.laurence.blog.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag,Integer>
{
	Tag findByTid(Integer tid);
}
