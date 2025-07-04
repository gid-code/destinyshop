package com.gidcode.destinyshop.service.product;

import java.util.List;
import java.util.Optional;

import com.gidcode.destinyshop.controller.CategoryController;
import com.gidcode.destinyshop.dto.ImageDto;
import com.gidcode.destinyshop.dto.ProductDto;
import com.gidcode.destinyshop.exception.AlreadyExistException;
import com.gidcode.destinyshop.exception.ProductNotFoundException;
import com.gidcode.destinyshop.model.Category;
import com.gidcode.destinyshop.model.Image;
import com.gidcode.destinyshop.repository.CategoryRepository;
import com.gidcode.destinyshop.repository.ImageRepository;
import com.gidcode.destinyshop.request.AddProductRequest;
import com.gidcode.destinyshop.request.UpdateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest addProductRequest) {
        if (productRepository.existsByNameAndBrand(addProductRequest.name(),addProductRequest.brand())){
            throw new AlreadyExistException(addProductRequest.brand()+" "+addProductRequest.name()+" already exist. You may update instead!");
        }
        Category category = Optional.ofNullable(
                categoryRepository.findByName(addProductRequest.category().getName())
        ).orElseGet( () -> {
            Category newCategory = new Category(addProductRequest.category().getName());
            return categoryRepository.save(newCategory);
        });

        return productRepository.save(createProduct(addProductRequest,category));
    }

    private Product createProduct(AddProductRequest addProductRequest, Category category) {
        return new Product(
                addProductRequest.name(),
                addProductRequest.brand(),
                addProductRequest.price(),
                addProductRequest.inventory(),
                addProductRequest.description(),
                category
        );
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product with id: "+id+" not found"));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> {throw new ProductNotFoundException("Product with id: "+id+" not found");
                });
    }

    public Product updateProductById(UpdateProductRequest request, long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product with id: "+id+" not found"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest updateProductRequest) {
        existingProduct.setName(updateProductRequest.name());
        existingProduct.setInventory(updateProductRequest.inventory());
        existingProduct.setBrand(updateProductRequest.brand());
        existingProduct.setPrice(updateProductRequest.price());
        existingProduct.setDescription(updateProductRequest.description());
        existingProduct.setCategory(categoryRepository.findByName(updateProductRequest.category().getName()));

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String band) {
        return productRepository.findByBrand(band);
    }

    @Override
    public List<Product> getProductsByCategoryNameAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String product) {
        return productRepository.findByName(product);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String product) {
        return productRepository.findByBrandAndName(brand, product);
    }

    @Override
    public long countProductsByBandAndName(String brand, String product) {
        return productRepository.countByBrandAndName(brand, product);
    }
    
    @Override
    public ProductDto convertToDto(Product product){
        return product.toProductDto();
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Product> getProductsByCategoryNameAndName(String category, String name) {
        return productRepository.findByCategoryNameAndName(category, name);
    }

    @Override
    public List<Product> getProductsByCategoryNameAndBrandAndName(String categoryName, String brand, String name) {
        return productRepository.findByCategoryNameAndBrandAndName(categoryName, brand, name);
    }
}
