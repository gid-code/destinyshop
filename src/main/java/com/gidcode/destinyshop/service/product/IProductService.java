package com.gidcode.destinyshop.service.product;

import java.util.List;

import com.gidcode.destinyshop.dto.ProductDto;
import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.request.AddProductRequest;
import com.gidcode.destinyshop.request.UpdateProductRequest;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(long id);
    void deleteProductById(long id);
    Product updateProductById(UpdateProductRequest request, long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryName(String category);
    List<Product> getProductsByBrand(String band);
    List<Product> getProductsByCategoryNameAndBrand(String category, String brand);
    List<Product> getProductsByName(String product);
    List<Product> getProductsByBrandAndName(String brand, String product);
    long countProductsByBandAndName(String brand, String product);
    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
    List<Product> getProductsByCategoryNameAndBrandAndName(String categoryName, String brand, String name);
    List<Product> getProductsByCategoryNameAndName(String categoryName, String name);
}
