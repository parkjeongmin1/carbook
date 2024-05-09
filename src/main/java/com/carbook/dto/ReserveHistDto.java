package com.carbook.dto;

import com.carbook.constant.ReserveStatus;
import com.carbook.entity.Reserve;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReserveHistDto {
    public ReserveHistDto(Reserve reserve) {
    this.reserveId = reserve.getId();
    this.reserveDate = reserve.getReserveDate()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    this.reserveStatus = reserve.getReserveStatus();
}

    private Long reserveId;

    private String reserveDate;

    private ReserveStatus reserveStatus;

    private List<ReserveCarDto> reserveCarDtoList = new ArrayList<>();

    public void addReserveCarDto(ReserveCarDto reserveCarDto){
        this.reserveCarDtoList.add(reserveCarDto);
    }
}