package com.demo.social.Data.DTOs.Post;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostWithImage {

    private Integer id;
    private String caption;
    private Integer creator;
    private Integer imageId;
    private String imagePath;
    private Date createdAt;

    public PostWithImage(Object[] tuple) {
        this.id = (Integer) tuple[0];
        this.caption = (String) tuple[1];
        this.creator = (Integer) tuple[2];
        this.imageId = (Integer) tuple[3];
        this.imagePath = (String) tuple[4];
        this.createdAt = (Date) tuple[5];
    }
}
