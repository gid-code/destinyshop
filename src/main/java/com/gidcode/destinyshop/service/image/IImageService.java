package com.gidcode.destinyshop.service.image;

import com.gidcode.destinyshop.dto.ImageDto;
import com.gidcode.destinyshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImage(List<MultipartFile> files, long productId);
    void updateImage(MultipartFile file, long imageId);
}
