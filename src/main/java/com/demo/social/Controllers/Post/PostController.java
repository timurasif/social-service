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
    public Response<Object> createPost(@RequestHeader("user-id") String userId, @RequestParam("json") String json, @RequestParam("image") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CreatePostRequest createPostRequest = mapper.readValue(json, CreatePostRequest.class);

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
    public Response<Object> updatePost(@RequestHeader("user-id") String userId, @PathVariable("id") Integer id, @RequestBody UpdatePostRequest updatePostRequest) {
        CreatePostResponse createdPost = postService.updatePost(id, updatePostRequest.getCaption(), userId);

        return new Response<>(Constants.HttpStatusCodes.SUCCESS, Constants.POST_UPDATED, createdPost);
    }

    @GetMapping()
    @Operation(summary = "Get posts.")
    public Response<Object> getPosts(@RequestParam(name = "cursor", defaultValue = "1") int cursor,
                                       @RequestParam(name = "length", defaultValue = "5") int pageSize) {
        List<PostWithComments> postsWithComments = postService.getPosts(cursor, pageSize);

        return new Response<>(Constants.HttpStatusCodes.SUCCESS, Constants.POST_FETCHED, postsWithComments);
    }

}
