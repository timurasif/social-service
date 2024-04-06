package com.demo.social.Services.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ImageTransferInterface {

    String uploadImage(MultipartFile file) throws IOException;
    ByteArrayInputStream downloadImage(String imageUrl);

}
