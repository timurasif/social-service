package com.demo.social.Data.DTOs.Image;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetImageRequest {
    private String imageUrl;
}
