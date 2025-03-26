package com.gidcode.destinyshop.controller;

import com.gidcode.destinyshop.dto.ProductDto;
import com.gidcode.destinyshop.exception.AlreadyExistException;
import com.gidcode.destinyshop.exception.ProductNotFoundException;
import com.gidcode.destinyshop.model.Product;
import com.gidcode.destinyshop.request.AddProductRequest;
import com.gidcode.destinyshop.request.UpdateProductRequest;
import com.gidcode.destinyshop.response.ApiResponse;
import com.gidcode.destinyshop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/product")
public class ProductController extends BaseController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String categoryName) {
        try {
            List<Product> products;
            if (brand != null && categoryName != null && name != null) {
                products = productService.getProductsByCategoryNameAndBrandAndName(categoryName, brand, name);
            } else if (brand != null && name != null) {
                products = productService.getProductsByBrandAndName(brand, name);
            } else if (brand != null && categoryName != null) {
                products = productService.getProductsByCategoryNameAndBrand(categoryName, brand);
            } else if (name != null && categoryName != null) {
                products = productService.getProductsByCategoryNameAndName(categoryName, name);
            } else if (name != null) {
                products = productService.getProductsByName(name);
            } else if (brand != null) {
                products = productService.getProductsByBrand(brand);
            } else if (categoryName != null) {
                products = productService.getProductsByCategoryName(categoryName);
            } else {
                products = productService.getAllProducts();
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ProductNotFoundException e) {
            return handleException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
        try {
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("Product add success", product));
        } catch (AlreadyExistException e) {
            return handleException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request,@PathVariable Long productId) {
        try {
            Product product = productService.updateProductById(request,productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product update success", productDto));
        } catch (ProductNotFoundException e) {
            return handleException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success", productId));
        } catch (ProductNotFoundException e) {
            return handleException(e);
        }
    }

}
