package com.gidcode.destinyshop.service.image;

import com.gidcode.destinyshop.dto.ImageDto;
import com.gidcode.destinyshop.exception.ImageNotFoundException;
import com.gidcode.destinyshop.model.Image;
import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.repository.ImageRepository;
import com.gidcode.destinyshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image of id "+id+" not found!"));
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(
                        imageRepository::delete,
                        () -> {throw new ImageNotFoundException("Image of id "+id+" not found!");}
                );
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, long productId) {
        Product product = productService.getProductById(productId);
        String downloadUrlPath = "/api/v1/images/";

        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setDownloadUrl(downloadUrlPath);
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(downloadUrlPath+savedImage.getId());
                imageRepository.save(
                        savedImage
                );

                savedImageDtos.add(
                        new ImageDto(
                                savedImage.getId(),
                                savedImage.getFileName(),
                                savedImage.getDownloadUrl()
                        )
                );
            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }


        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, long imageId) {
        Image existingImage = getImageById(imageId);
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(existingImage);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
