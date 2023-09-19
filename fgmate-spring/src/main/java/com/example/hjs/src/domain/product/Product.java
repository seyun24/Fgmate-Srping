package com.example.hjs.src.domain.product;

import com.example.hjs.src.domain.BaseEntity;
import com.example.hjs.src.domain.refrigerator.Refrigerator;
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
@Table(name = "Products")
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    private String productName;
    private String productImg;
    private String date;
    private String description;
    private Long refrigeratorId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "refrigeratorId")
//    private Refrigerator refrigerator;

    protected Product (){

    }

    public void setProductImg(String productImg){
        this.productImg=productImg;
    }
}
