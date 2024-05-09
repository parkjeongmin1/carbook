package com.carbook.dto;

import com.carbook.constant.CarSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSearchDto {
    private String searchDateType;
    private CarSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";
}
