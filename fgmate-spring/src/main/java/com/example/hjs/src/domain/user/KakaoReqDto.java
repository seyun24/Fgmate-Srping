package com.example.hjs.src.domain.user;

import lombok.*;

@Data
@Builder
public class KakaoReqDto {
    private long id;
    private String email;
}
