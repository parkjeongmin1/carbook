package com.carbook.dto;

import com.carbook.entity.ReserveCar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveCarDto {
    public ReserveCarDto(ReserveCar reserveCar, String imgUrl){
        this.carNm = reserveCar.getCar().getCarNm();
        this.count = reserveCar.getCount();
        this.reservePrice = reserveCar.getReservePrice();
        this.imgUrl = imgUrl;
    }

    private String carNm;

    private int count;

    private int reservePrice;

    private String imgUrl;
}
