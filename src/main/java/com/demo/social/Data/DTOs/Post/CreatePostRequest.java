package com.demo.social.Data.DTOs.Post;

import com.demo.social.Data.Entities.PostEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private String caption;

    public static PostEntity toEntity(CreatePostRequest request) {
        return PostEntity.builder()
                .caption(request.getCaption())
                .build();
    }

}
