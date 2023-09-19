package com.example.hjs.src.domain.emotion;

import com.example.hjs.src.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity

@DynamicInsert
@DynamicUpdate
@Table(name = "Emoticons")
public class Emoticon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eListId")
    private Integer eListId;

    @Column(name = "emoticonLink")
    private String emoticonLink;

}
