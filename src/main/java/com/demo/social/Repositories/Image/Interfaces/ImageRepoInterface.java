package com.demo.social.Repositories.Image.Interfaces;

import com.demo.social.Data.Entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepoInterface extends JpaRepository<ImageEntity, Integer> {

}
