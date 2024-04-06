package com.demo.social.Controllers.Comment;

import com.demo.social.Data.DTOs.Comment.CreateCommentRequest;
import com.demo.social.Data.DTOs.Comment.CreateCommentResponse;
import com.demo.social.Data.DTOs.Post.CreatePostRequest;
import com.demo.social.Data.DTOs.Post.CreatePostResponse;
import com.demo.social.Data.Response;
import com.demo.social.Services.Comment.CommentService;
import com.demo.social.Services.Post.PostService;
import com.demo.social.Utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Validated
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    @Operation(summary = "Create new comment.")
    public Response<Object> createComment(@RequestHeader("user-id") String userId, @RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResponse createdComment = commentService.createNewComment(CreateCommentRequest.toEntity(createCommentRequest), userId);

        return new Response<>(Constants.HttpStatusCodes.CREATED, Constants.COMMENT_CREATED, createdComment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a comment.")
    public Response<Object> deleteComment(@RequestHeader("user-id") String userId, @PathVariable("id") Integer id) {
        commentService.deleteComment(userId, id);

        return new Response<>(Constants.HttpStatusCodes.CREATED, Constants.COMMENT_DELETED, null);
    }

}
