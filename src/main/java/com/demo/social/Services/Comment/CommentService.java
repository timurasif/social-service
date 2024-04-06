package com.demo.social.Services.Comment;

import com.demo.social.Data.DTOs.Comment.CreateCommentResponse;
import com.demo.social.Data.Entities.CommentEntity;
import com.demo.social.Repositories.Comment.Interfaces.CommentRepoInterface;
import com.demo.social.Repositories.Post.Interfaces.PostRepoInterface;
import com.demo.social.exceptions.ExceptionHandlers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepoInterface commentRepoInterface;
    private final PostRepoInterface postRepoInterface;

    public CreateCommentResponse createNewComment(CommentEntity comment, String userId) {

        try {
            comment.setCreator(Integer.valueOf(userId));
        } catch (NumberFormatException e) {
            throw new ExceptionHandlers.WrongUserIdException("Incorrect User ID: " + userId);
        }

        postRepoInterface.findById(comment.getPostId())
                .orElseThrow(() -> {
                    return new ExceptionHandlers.PostNotFoundException("Post with ID: " + comment.getPostId() + " not found!");
                });

        CommentEntity createdComment = commentRepoInterface.save(comment);

        return CreateCommentResponse.builder()
                .id(createdComment.getId())
                .content(createdComment.getContent())
                .creator(createdComment.getCreator())
                .postId(createdComment.getPostId())
                .createdAt(createdComment.getCreatedAt())
                .build();
    }

    public void deleteComment(String userId, Integer id) {

        CommentEntity comment = commentRepoInterface.findById(id)
                .orElseThrow(() -> {
                    return new ExceptionHandlers.CommentNotFoundException("Comment with ID: " + id + " not found!");
                });

        try {
            if(Integer.valueOf(userId).equals(comment.getCreator())){
                commentRepoInterface.delete(comment);
            }else{
                throw new ExceptionHandlers.UpdateForbiddenException("Comment ID: " + id + " does not belong to User Id: " + userId);
            }
        } catch (NumberFormatException e) {
            throw new ExceptionHandlers.WrongUserIdException("Incorrect User ID: " + userId);
        }
    }

}
