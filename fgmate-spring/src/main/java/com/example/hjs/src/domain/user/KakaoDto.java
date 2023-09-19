package com.example.hjs.src.domain.user;

import lombok.*;


@Data
@Builder
public class KakaoDto {
    private Long userId;
    private String jwt;
    private int loginInfo;
}
