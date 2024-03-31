package com.myblog7.repository;

import com.myblog7.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    custom method in repository layer to find comments by post_id, findByPostId is custom method and findById is built-in method
    List<Comment> findByPostId(long postId);
}
