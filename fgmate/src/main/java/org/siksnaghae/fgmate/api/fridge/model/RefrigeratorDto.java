package org.siksnaghae.fgmate.api.fridge.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefrigeratorDto {
    private String refrigeratorName;
    private Long refrigeratorId;

    @QueryProjection
    public RefrigeratorDto(String refrigeratorName, Long refrigeratorId){
        this.refrigeratorName=refrigeratorName;
        this.refrigeratorId=refrigeratorId;
    }
}
