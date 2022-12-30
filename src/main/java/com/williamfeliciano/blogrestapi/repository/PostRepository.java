package com.williamfeliciano.blogrestapi.repository;

import com.williamfeliciano.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
