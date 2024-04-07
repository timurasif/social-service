package com.demo.social.Services.Image;

import com.demo.social.Data.Entities.ImageEntity;
import com.demo.social.Repositories.Image.Interfaces.ImageRepoInterface;
import com.demo.social.Utils.Constants;
import com.demo.social.Utils.Helpers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepoInterface imageRepoInterface;
    private final GcpImageTransferService gcpImageTransferService;
    private final Helpers helpers;


    public ImageEntity saveImage(ImageEntity image){
        return imageRepoInterface.save(image);
    }

    public byte[] getImage(String imagePath){
        byte[] imageBytes = null;
        ByteArrayInputStream imageByteArray = gcpImageTransferService.downloadImage(imagePath);
        if(imageByteArray != null){
            ByteArrayInputStream processesImageBytes = helpers.resizeImage(imageByteArray, Constants.IMAGE_HEIGHT, Constants.IMAGE_WIDTH);
            imageBytes = helpers.convertInputStreamToByteArray(processesImageBytes);
        }
        return imageBytes;
    }

}
