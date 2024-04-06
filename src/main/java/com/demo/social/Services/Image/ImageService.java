package com.demo.social.Services.Image;

import com.demo.social.Data.Entities.ImageEntity;
import com.demo.social.Repositories.Image.Interfaces.ImageRepoInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepoInterface imageRepoInterface;

    public ImageEntity saveImage(ImageEntity image){
        return imageRepoInterface.save(image);
    }

}
