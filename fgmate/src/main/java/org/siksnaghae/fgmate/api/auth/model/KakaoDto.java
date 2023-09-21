package org.siksnaghae.fgmate.api.auth.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class KakaoDto {
    private Long userId;
    private String jwt;
    private int loginInfo;
}
