package com.laurence.blog.Repository;

import com.laurence.blog.Model.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photos,Integer>
{
}
