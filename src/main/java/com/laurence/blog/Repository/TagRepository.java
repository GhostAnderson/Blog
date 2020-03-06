package com.laurence.blog.Repository;

import com.laurence.blog.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer>
{
	Tag findByTid(Integer tid);
}
