package com.demo.social.Repositories.Post.Interfaces;

import com.demo.social.Data.DTOs.Post.PostWithImage;
import com.demo.social.Data.Entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface PostRepoInterface extends JpaRepository<PostEntity, Integer> {

    @Query("SELECT p, i.imagePath FROM PostEntity p " +
            "LEFT JOIN p.image i " +
            "WHERE p.id > :cursor ORDER BY p.id ASC")
    List<Object[]> findPostsWithImageAfterCursor(@Param("cursor") int cursor);

    default List<PostWithImage> findPostsWithImageAfterCursor(int cursor, int pageSize) {
        List<Object[]> results = findPostsWithImageAfterCursor(cursor);
        List<PostWithImage> postWithImages = new ArrayList<>();
        for (Object[] result : results) {
            PostEntity post = (PostEntity) result[0];
            String imagePath = (String) result[1];
            PostWithImage postWithImage = new PostWithImage(
                    post.getId(),
                    post.getCaption(),
                    post.getCreator(),
                    post.getImageId(),
                    imagePath,
                    post.getCreatedAt()
            );
            postWithImages.add(postWithImage);
        }
        return postWithImages;
    }
}

