package com.example.hjs.src.controller;

import com.example.hjs.config.BaseException;
import com.example.hjs.config.ApiResponse;
import com.example.hjs.src.domain.product.Product;
import com.example.hjs.src.domain.product.ProductDto;
import com.example.hjs.src.service.ProductService;
import com.example.hjs.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/app/products")
@RequiredArgsConstructor
public class ProductController {
    private final JwtService jwtService;
    private final ProductService productService;


    @GetMapping("/{refriId}")
    public ApiResponse<List<ProductDto>> findAllProducts(@PathVariable("refriId") Long refriId) {
        try {
            List<ProductDto> products = productService.findAllProducts(refriId);
            return new ApiResponse<>(products);
        } catch (BaseException exception) {
            return new ApiResponse<>(exception.getStatus());
        }
    }

    @GetMapping
    public ApiResponse<ProductDto> findProduct(@RequestParam Long productId) {
        try {
            ProductDto product = productService.findProduct(productId);
            return new ApiResponse<>(product);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("")
    public ApiResponse<String> saveProduct(@RequestBody Product product) {
        try {
            productService.saveProduct(product);
            return new ApiResponse<>("성공");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> testProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile file)
    {
        try {
            String fileUrl = productService.test(file);
            productService.saveProduct(product,fileUrl);
            return new ApiResponse<>(fileUrl);
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return new ApiResponse<>("선택하신 식품이 삭제되었습니다.");
        } catch (BaseException exception) {
            return new ApiResponse<>((exception.getStatus()));
        }
    }
}
