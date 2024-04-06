package com.demo.social.Data.DTOs.Comment;

import com.demo.social.Data.Entities.CommentEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentResponse {
    Integer id;
    private String content;
    private Integer creator;
    private Integer postId;
    private Date createdAt;

    public static CreateCommentResponse fromEntity(CommentEntity entity) {
        return CreateCommentResponse.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .creator(entity.getCreator())
                .postId(entity.getPostId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
