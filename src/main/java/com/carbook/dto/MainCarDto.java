package com.carbook.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCarDto {
    private Long id;
    private String carNm;
    private String carDetail;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainCarDto(Long id, String carNm, String carDetail, String imgUrl, Integer price) {
        this.id = id;
        this.carNm = carNm;
        this.carDetail = carDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
