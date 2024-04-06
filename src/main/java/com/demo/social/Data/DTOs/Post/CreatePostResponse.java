package com.demo.social.Data.DTOs.Post;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {
    Integer id;
    private String caption;
    private Integer creator;
    private Integer imageId;
    private Date createdAt;
}
