package com.eeerrorcode.pilllaw.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRatingDto {
    private Long pno;
    private String pname;
    private String company;
    private double avgRating; 
}
