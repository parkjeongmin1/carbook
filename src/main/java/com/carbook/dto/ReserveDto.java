package com.carbook.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveDto {
    @NotNull(message = "상품 아이디는 필수 입력입니다.")
    private Long carId;

    @Min(value = 1, message = "최소 예약시간은 1시간 이상 입니다")
    @Max(value = 999, message = "최대 예약시간은 999시간 입니다")
    private int count;
}
