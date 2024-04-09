package com.demo.social.Controllers.Post;

import com.demo.social.Data.DTOs.Post.CreatePostRequest;
import com.demo.social.Data.DTOs.Post.CreatePostResponse;
import com.demo.social.Data.DTOs.Post.PostWithComments;
import com.demo.social.Data.DTOs.Post.UpdatePostRequest;
import com.demo.social.Data.Response;
import com.demo.social.Services.Post.PostService;
import com.demo.social.Utils.Constants;
import com.demo.social.Utils.Helpers;
import com.demo.social.exceptions.ExceptionHandlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@Validated
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final Helpers helpers;

    @PostMapping()
    @Operation(summary = "Create new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created"),
            @ApiResponse(responseCode = "400", description = "Image file not found"),
            @ApiResponse(responseCode = "403", description = "Wrong image format"),
            @ApiResponse(responseCode = "403", description = "Wrong image size"),
    })
    public Response<Object> createPost(@RequestHeader("user-id") String userId, @RequestParam("body") String body, @RequestParam("image") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CreatePostRequest createPostRequest = mapper.readValue(body, CreatePostRequest.class);

        if (file.isEmpty()) {
            return new Response<>(Constants.HttpStatusCodes.BAD_REQUEST, Constants.IMAGE_NOT_FOUND, null);
        }

        if(!helpers.isImageFormatValid(file.getContentType())){
            throw new ExceptionHandlers.ImageException(Constants.WRONG_IMAGE_FORMAT);
        }
        if(!helpers.isImageSizeValid(file.getSize())){
            throw new ExceptionHandlers.ImageException(Constants.WRONG_IMAGE_SIZE);
        }

        CreatePostResponse createdPost = postService.createNewPost(CreatePostRequest.toEntity(createPostRequest), file, userId);

        return new Response<>(Constants.HttpStatusCodes.CREATED, Constants.POST_CREATED, createdPost);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update post caption.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated"),
            @ApiResponse(responseCode = "400", description = "Post not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "400", description = "Post does not belong to this user"),
    })
    public Response<Object> updatePost(@RequestHeader("user-id") String userId, @PathVariable("id") Integer id, @RequestBody UpdatePostRequest updatePostRequest) {
        CreatePostResponse createdPost = postService.updatePost(id, updatePostRequest.getCaption(), userId);

        return new Response<>(Constants.HttpStatusCodes.SUCCESS, Constants.POST_UPDATED, createdPost);
    }

    @GetMapping()
    @Operation(summary = "Get posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post fetched")
    })
    public Response<Object> getPosts(@RequestParam(name = "cursor", defaultValue = "1") int cursor,
                                       @RequestParam(name = "length", defaultValue = "5") int pageSize) {
        List<PostWithComments> postsWithComments = postService.getPosts(cursor, pageSize);

        return new Response<>(Constants.HttpStatusCodes.SUCCESS, Constants.POST_FETCHED, postsWithComments);
    }

}
