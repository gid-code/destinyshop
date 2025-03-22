package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.dto.ImageDto;
import com.gidcode.destinyshop.exception.ImageNotFoundException;
import com.gidcode.destinyshop.model.Image;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/image")
public class ImageController {
    private final IImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam long productId) {
        try {
            List<ImageDto> images = imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success!",images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length())
        );
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null ) {
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success!", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null ) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
