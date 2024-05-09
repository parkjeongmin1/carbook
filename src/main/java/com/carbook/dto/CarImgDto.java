package com.carbook.dto;

import com.carbook.entity.CarImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CarImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static CarImgDto of(CarImg carImg) {

        return modelMapper.map(carImg, CarImgDto.class);
    }

}
