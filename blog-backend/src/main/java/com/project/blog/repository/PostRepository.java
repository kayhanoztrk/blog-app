package com.project.blog.repository;

import com.project.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
