package org.siksnaghae.fgmate.api.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoReqDto {
    private long id;
    private String email;
}
