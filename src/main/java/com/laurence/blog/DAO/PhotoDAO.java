package com.laurence.blog.DAO;

import com.laurence.blog.Model.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoDAO extends JpaRepository<Photos,Integer>
{
}
