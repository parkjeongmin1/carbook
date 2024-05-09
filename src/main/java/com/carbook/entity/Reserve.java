package com.carbook.entity;

import com.carbook.constant.ReserveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reserves")
@Getter
@Setter
@ToString
public class Reserve {
    @Id
    @Column(name="reserve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reserveDate;

    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, fetch =  FetchType.LAZY
            , orphanRemoval = true)
    private List<ReserveCar> reserveCars = new ArrayList<>();

    public void addReserveCar(ReserveCar reserveCar) {
        this.reserveCars.add(reserveCar);
        reserveCar.setReserve(this);
    }

    public static Reserve createReserve(Member member, List<ReserveCar> reserveCarList) {
        Reserve reserve = new Reserve();
        reserve.setMember(member);

        for(ReserveCar reserveCar : reserveCarList) {
            reserve.addReserveCar(reserveCar);
        }

        reserve.setReserveStatus(ReserveStatus.RESERVE);
        reserve.setReserveDate(LocalDateTime.now());

        return reserve;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(ReserveCar reserveCar : reserveCars) {
            totalPrice += reserveCar.getReservePrice();
        }
        return totalPrice;
    }

    public void cancelReserve() {
        this.reserveStatus = ReserveStatus.CANCEL;

        for(ReserveCar reserveCar: reserveCars) {
            reserveCar.cancel();
        }
    }
}
