package com.example.hjs.src.domain.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@NoArgsConstructor
public class UserDto {

    private String name;

    private Long id;

    private String email;

    private String status;


    @QueryProjection
    public UserDto(User user){
        this.name=user.getName();
        this.id =user.getInfoId();
        this.status=user.getStatus();
    }
}
