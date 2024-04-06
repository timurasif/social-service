package com.demo.social.Utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class Helpers {

    public boolean isImageFormatValid(String imageFormat){
        return imageFormat != null && imageFormat.matches("image/(png|jpg|bmp)");
    }

    public boolean isImageSizeValid(long imageSize){
        return imageSize < Constants.MAX_IMAGE_SIZE_ALLOWED;
    }

    public byte[] convertInputStreamToByteArray(ByteArrayInputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
