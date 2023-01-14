package com.project.blog.repository;

import com.project.blog.entity.Post;
import com.project.blog.model.response.PostCommentedResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query(value = "SELECT p FROM Post p WHERE p.isPublished=:isPublished and p.user.id = :userId")
    List<Post> findAllDraftOrPublishedPost(@Param("isPublished")
                                           boolean isPublished, @Param("userId")
                                           Long userId);

    @Query(value = "SELECT p FROM Post p WHERE p.isPublished=true ORDER BY p.id DESC")
    List<Post> findAllPublished();

    @Query(value="SELECT new com.project.blog.model.response.PostCommentedResponse(p.id,p.title,p.text,p.createdAt,COUNT(c.post.id) as num_comments, p.isPublished)" +
            " FROM Post p INNER JOIN Comment c on p.id = c.post.id and p.user.id=:userId GROUP BY " +
            " c.post.id ORDER BY num_comments DESC")
    List<PostCommentedResponse> findCommentedPost(Long userId);
}
