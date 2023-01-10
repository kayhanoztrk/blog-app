package com.project.blog.repository;

import com.project.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT p.id,p.title,p.text, p.tags FROM Post p WHERE p.id=:postId",nativeQuery = true)
    Post findTagNotExistsPostByPostId(Long postId);
    List<Post> findByUserId(Long userId);

}
