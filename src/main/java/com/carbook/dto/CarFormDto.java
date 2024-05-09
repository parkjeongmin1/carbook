package com.carbook.dto;

import com.carbook.constant.CarSellStatus;
import com.carbook.entity.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CarFormDto {
    private Long id;

    @NotBlank(message = "자동차명은 필수 입력값입니다.")
    private String carNm;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private int price;

    @NotNull(message = "재고는 필수 입력값입니다.")
    private int stockNumber;

    @NotBlank(message = "상세설명은 필수 입력값입니다.")
    private String carDetail;

    private CarSellStatus carSellStatus;

    private List<CarImgDto> carImgDtoList = new ArrayList<>();

    private List<Long> carImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Car creatCar() {
        return modelMapper.map(this, Car.class);
    }
    public static CarFormDto of(Car car) {
        return modelMapper.map(car, CarFormDto.class);
    }
}
