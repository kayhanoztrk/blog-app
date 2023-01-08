package com.project.blog.repository;

import com.project.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t.text, t.id from Tag t left join t.postList pList WHERE pList.id = :postId", nativeQuery = true)
    List<Tag> findAllByPostId(Long postId);
}
