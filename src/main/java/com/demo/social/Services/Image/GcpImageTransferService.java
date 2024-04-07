package com.demo.social.Services.Image;

import com.demo.social.Utils.Constants;
import com.demo.social.Utils.Helpers;
import com.demo.social.exceptions.ExceptionHandlers;
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

    private final Helpers helpers;

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
        String[] blobInfo = helpers.extractBlobInfoFromUrl(imageUrl);
        String blobName = blobInfo[0];
        Long blobGeneration = Long.valueOf(blobInfo[1]);

        BlobId blobId = BlobId.of(bucketName, blobName, blobGeneration);

        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.get(blobId);

        if (blob != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.downloadTo(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } else {
            throw new ExceptionHandlers.ImageNotFoundException("Image not found.");
        }
    }

}
