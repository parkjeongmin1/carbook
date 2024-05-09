package com.carbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reserve_car")
@Getter
@Setter
@ToString
public class ReserveCar {
    @Id
    @Column(name="reserve_car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int reservePrice;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reserve_id")
    private Reserve reserve;


    public static ReserveCar createReserveCar(Car car, int count) {
        ReserveCar reserveCar = new ReserveCar();
        reserveCar.setCar(car);
        reserveCar.setCount(count);
        reserveCar.setReservePrice(car.getPrice());

        car.removeStock(count);

        return reserveCar;
    }

    public int getTotalPrice() {
        return reservePrice * count;
    }

    public void cancel() {
        this.getCar().addStock(count);
    }
}
