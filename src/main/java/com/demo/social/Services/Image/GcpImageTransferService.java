package com.demo.social.Services.Image;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GcpImageTransferService implements ImageTransferInterface{

    @Value("${gcp.image-bucket.name}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(bucketName);
        Blob blob = bucket.create(fileName, file.getInputStream(), file.getContentType());

        return blob.getMediaLink();
    }

    @Override
    public ByteArrayInputStream downloadImage(String imageUrl) {
        System.out.println("Image URL: " + imageUrl);
        System.out.println("Bucket: " + bucketName);

        String[] blobInfo = extractBlobInfoFromUrl(imageUrl);
        System.out.println("Blob name: " + blobInfo[0]);
        System.out.println("Blob generation: " + blobInfo[1]);

        BlobId blobId = BlobId.of(bucketName, blobInfo[0], Long.valueOf(blobInfo[1]));
        System.out.println("BlobId: " + blobId);

        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.get(blobId);

        if (blob != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.downloadTo(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } else {
            System.err.println("Blob not found for BlobId: " + blobId);
            return null;
        }
    }

    private static String[] extractBlobInfoFromUrl(String imageUrl) {
        int startIndex = imageUrl.indexOf("generation=") + "generation=".length();
        int endIndex = imageUrl.indexOf("&", startIndex);
        String generationString = imageUrl.substring(startIndex, endIndex);

        int lastSlashIndex = imageUrl.lastIndexOf('/');
        String blobName = imageUrl.substring(lastSlashIndex + 1, startIndex - "generation=".length() - 1);

        return new String[]{blobName, generationString};
    }

}
