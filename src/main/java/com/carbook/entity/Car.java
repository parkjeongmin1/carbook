package com.carbook.entity;

import com.carbook.constant.CarSellStatus;
import com.carbook.dto.CarFormDto;
import com.carbook.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="car")
@Getter
@Setter
@ToString
public class Car extends Base{
    @Id
    @Column(name="car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String carNm;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false, columnDefinition = "longtext")
    private String carDetail;

    @Enumerated(EnumType.STRING)
    private CarSellStatus carSellStatus;

    //엔티티수정
    public void updateCar(CarFormDto carFormDto) {
        this.carNm = carFormDto.getCarNm();
        this.price = carFormDto.getPrice();
        this.stockNumber = carFormDto.getStockNumber();
        this.carDetail = carFormDto.getCarDetail();
        this.carSellStatus = carFormDto.getCarSellStatus();
    }

    //재고감소
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;

        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. "
                    + "현재 재고수량: " + this.stockNumber);
        }

        this.stockNumber = restStock;
    }

//    재고증가
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
