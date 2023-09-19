package com.example.hjs.src.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.hjs.config.BaseException;
import com.example.hjs.src.dao.product.ProductRepository;
import com.example.hjs.src.domain.product.Product;
import com.example.hjs.src.domain.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.hjs.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.hjs.config.BaseResponseStatus.IMAGE_UPLOAD_FAIL;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<ProductDto> findAllProducts(Long refrigeratorId) throws BaseException {
        try {
            List<Product> productList = productRepository.findByRefrigeratorId(refrigeratorId).orElse(null);
            return productList.stream()
                    .map(product -> new ProductDto(product))
                    .collect(Collectors.toList());

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ProductDto findProduct(Long productsId) throws BaseException {
        try {
            return productRepository.findByProductIdDtl(productsId).orElse(null);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void saveProduct(Product product) throws BaseException {
        try {
            productRepository.save(product);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void saveProduct(Product product, String fileUrl) throws BaseException {
        try {
            product.setProductImg(fileUrl);
            productRepository.save(product);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public String test(MultipartFile file) throws BaseException {
        try {

            String fileName = file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName,fileName, file.getInputStream(), metadata);
            URL fileUrl = amazonS3Client.getUrl(bucketName, fileName);

            return fileUrl.toString();
        } catch (Exception exception) {
            throw new BaseException(IMAGE_UPLOAD_FAIL);
        }
    }

    public void deleteProduct(Long productId) throws BaseException {
        try {
            productRepository.deleteById(productId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
