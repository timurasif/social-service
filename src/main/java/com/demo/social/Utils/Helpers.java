package com.demo.social.Utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class Helpers {

    public boolean isImageFormatValid(String imageFormat){
        return imageFormat != null && imageFormat.matches("image/(png|jpg|jpeg|bmp)");
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

    public String[] extractBlobInfoFromUrl(String imageUrl) {
        int startIndex = imageUrl.indexOf("generation=") + "generation=".length();
        int endIndex = imageUrl.indexOf("&", startIndex);
        String generationString = imageUrl.substring(startIndex, endIndex);

        int lastSlashIndex = imageUrl.lastIndexOf('/');
        String blobNameWithPercentEncoding = imageUrl.substring(lastSlashIndex + 1, startIndex - "generation=".length() - 1);

        String blobName = blobNameWithPercentEncoding.replace(Constants.SPACE_REPRESENTATION, " ");

        return new String[]{blobName, generationString};
    }

    public ByteArrayInputStream resizeImage(ByteArrayInputStream inputStream, int targetHeight, int targetWidth) {
        try {
            BufferedImage originalImage = ImageIO.read(inputStream);

            Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(resultingImage, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to resize image");
        }
    }
}
