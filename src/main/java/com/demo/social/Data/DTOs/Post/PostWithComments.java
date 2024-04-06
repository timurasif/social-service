package com.demo.social.Data.DTOs.Post;

import com.demo.social.Data.DTOs.Comment.CreateCommentResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostWithComments {
    private PostWithImage post;
    private List<CreateCommentResponse> comments = new ArrayList<>(2);
}
