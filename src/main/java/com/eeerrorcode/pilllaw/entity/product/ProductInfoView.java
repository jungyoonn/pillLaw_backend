package com.eeerrorcode.pilllaw.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "v_product_info") 
public class ProductInfoView {

    @Id
    @Column(name = "pno") 
    private Long pno;

    @Column(name = "pname")
    private String pname;

    @Column(name = "company")
    private String company;

    @Column(name = "effect")
    private String effect;

    @Column(name = "precautions")
    private String precautions;

    @Column(name = "price")
    private Integer price;

    @Column(name = "sale_price") 
    private Integer salePrice;

    @Column(name = "avg_rating")
    private Double avgRating;

    @Column(name = "review_likes")
    private Integer reviewLikes;
}