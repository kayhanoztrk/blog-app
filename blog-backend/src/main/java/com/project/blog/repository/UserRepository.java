package com.project.blog.repository;

import com.project.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
