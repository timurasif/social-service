package com.demo.social.Repositories.Comment.Interfaces;

import com.demo.social.Data.Entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepoInterface extends JpaRepository<CommentEntity, Integer> {

    @Query("SELECT c FROM CommentEntity c\n" +
            "WHERE c.postId = ?1 ORDER BY c.createdAt DESC Limit 2")
    List<CommentEntity> findLastTwoByPostId(int postId);

}
