package com.demo.social.Services.Post;

import com.demo.social.Data.DTOs.Comment.CreateCommentResponse;
import com.demo.social.Data.DTOs.Post.CreatePostResponse;
import com.demo.social.Data.DTOs.Post.PostWithComments;
import com.demo.social.Data.DTOs.Post.PostWithImage;
import com.demo.social.Data.Entities.CommentEntity;
import com.demo.social.Data.Entities.ImageEntity;
import com.demo.social.Data.Entities.PostEntity;
import com.demo.social.Repositories.Comment.Interfaces.CommentRepoInterface;
import com.demo.social.Repositories.Post.Interfaces.PostRepoInterface;
import com.demo.social.Services.Image.GcpImageTransferService;
import com.demo.social.Services.Image.ImageService;
import com.demo.social.Utils.Helpers;
import com.demo.social.exceptions.ExceptionHandlers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostService {

    private final PostRepoInterface postRepoInterface;
    private final CommentRepoInterface commentRepoInterface;
    private final GcpImageTransferService gcpImageTransferService;
    private final ImageService imageService;
    private final Helpers helpers;


    public CreatePostResponse createNewPost(PostEntity post, MultipartFile file, String userId) throws IOException {

        String imageUrl = gcpImageTransferService.uploadImage(file);

        ImageEntity image = ImageEntity
                .builder()
                .imagePath(imageUrl)
                .imageFormat(file.getContentType())
                .build();

        ImageEntity savedImage = imageService.saveImage(image);

        post.setImageId(savedImage.getId());
        try {
            post.setCreator(Integer.valueOf(userId));
        } catch (NumberFormatException e) {
            throw new ExceptionHandlers.WrongUserIdException("Incorrect User ID: " + userId);
        }

        PostEntity createdPost = postRepoInterface.save(post);

        return CreatePostResponse.builder()
                .id(createdPost.getId())
                .caption(createdPost.getCaption())
                .creator(createdPost.getCreator())
                .imageId(post.getImageId())
                .createdAt(createdPost.getCreatedAt())
                .build();
    }

    public CreatePostResponse updatePost(Integer id, String caption, String userId) {

        PostEntity post = postRepoInterface.findById(id)
                .orElseThrow(() -> {
                    return new ExceptionHandlers.PostNotFoundException("Post with ID: " + id + " not found!");
                });

        try {
            if(!Objects.equals(post.getCreator(), Integer.valueOf(userId))){
                throw new ExceptionHandlers.UpdateForbiddenException("Post ID: " + id + " does not belong to User Id: " + userId);
            }
        } catch (NumberFormatException e) {
            throw new ExceptionHandlers.WrongUserIdException("Incorrect User ID: " + userId);
        }

        post.setCaption(caption);
        PostEntity createdPost = postRepoInterface.save(post);

        return CreatePostResponse.builder()
                .id(createdPost.getId())
                .caption(createdPost.getCaption())
                .creator(createdPost.getCreator())
                .createdAt(createdPost.getCreatedAt())
                .build();
    }

    public List<PostWithComments> getPosts(int cursor, int pageSize) {
        List<PostWithImage> posts = postRepoInterface.findPostsWithImageAfterCursor(cursor, pageSize);

        List<PostWithComments> postsWithComments = new ArrayList<>();

        for(PostWithImage post: posts){
            List<CommentEntity> comments = commentRepoInterface.findLastTwoByPostId(post.getId());
            List<CreateCommentResponse> commentResponses = comments.stream()
                    .map(CreateCommentResponse::fromEntity)
                    .collect(Collectors.toList());

            PostWithComments postWithComments = new PostWithComments(post, commentResponses);
            postsWithComments.add(postWithComments);
        }

        return postsWithComments;
    }

}
