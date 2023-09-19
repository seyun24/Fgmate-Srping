package com.example.hjs.src.domain.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductReqDto {
    private String productName;
    private String productImg;
    private String date;
    private String description;
    private Long refrigeratorId;
}
