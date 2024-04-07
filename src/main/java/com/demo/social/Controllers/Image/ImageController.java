package com.demo.social.Controllers.Image;

import com.demo.social.Data.DTOs.Image.GetImageRequest;
import com.demo.social.Services.Image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping()
    @Operation(summary = "Get Image.")
    public ResponseEntity<byte[]> getImage(@RequestBody GetImageRequest getImageRequest) {
        byte[] imageBinary = imageService.getImage(getImageRequest.getImageUrl());
        if (imageBinary == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBinary, headers, HttpStatus.OK);
    }

}
