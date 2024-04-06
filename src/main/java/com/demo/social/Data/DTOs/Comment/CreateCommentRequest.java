package com.demo.social.Data.DTOs.Comment;

import com.demo.social.Data.Entities.CommentEntity;
import com.demo.social.Data.Entities.PostEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private String content;

    private Integer postId;

    public static CommentEntity toEntity(CreateCommentRequest request) {
        return CommentEntity.builder()
                .content(request.getContent())
                .postId(request.getPostId())
                .build();
    }

}
