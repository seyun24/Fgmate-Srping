package com.example.hjs.src.domain.user;

import com.example.hjs.src.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Users")
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    private String name;
    @Column(name = "infoId")
    private Long infoId;
    private String email;

    @Column(name = "profileImg")
    private String profileImg;

    protected User() {

    }

    public void setName(String name){
        this.name=name;
    }
}
