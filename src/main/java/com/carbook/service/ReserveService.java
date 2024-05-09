package com.carbook.service;

import com.carbook.dto.ReserveCarDto;
import com.carbook.dto.ReserveDto;
import com.carbook.dto.ReserveHistDto;
import com.carbook.entity.*;
import com.carbook.repository.CarImgRepository;
import com.carbook.repository.CarRepository;
import com.carbook.repository.MemberRepository;
import com.carbook.repository.ReserveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveService {
    private final CarRepository carRepository;
    private final CarImgRepository carImgRepository;
    private final MemberRepository memberRepository;
    private final ReserveRepository reserveRepository;

    public Long reserve(ReserveDto reserveDto, String email) {

        Car car = carRepository.findById(reserveDto.getCarId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<ReserveCar> reserveCarList = new ArrayList<>();
        ReserveCar reserveCar = ReserveCar.createReserveCar(car, reserveDto.getCount());
        reserveCarList.add(reserveCar);

        Reserve reserve = Reserve.createReserve(member, reserveCarList);
        reserveRepository.save(reserve);

        return reserve.getId();
    }

    public Page<ReserveHistDto> getReserveList(String email, Pageable pageable) {

        List<Reserve> reserves = reserveRepository.findReserves(email, pageable);

        Long totalCount = reserveRepository.countReserve(email);

        List<ReserveHistDto> reserveHistDtos = new ArrayList<>();

        for (Reserve reserve : reserves) {
            ReserveHistDto reserveHistDto = new ReserveHistDto(reserve);

            List<ReserveCar> reserveCars = reserve.getReserveCars();

            for(ReserveCar reserveCar : reserveCars) {
                CarImg carImg = carImgRepository
                        .findByCarIdAndRepImgYn(reserveCar.getCar().getId(), "Y");

                if(carImg != null) {
                    ReserveCarDto reserveCarDto =
                            new ReserveCarDto(reserveCar, carImg.getImgUrl());
                    reserveHistDto.addReserveCarDto(reserveCarDto);
                }

            }

            reserveHistDtos.add(reserveHistDto);
        }

        return new PageImpl<>(reserveHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateReserve(Long reserveId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = reserve.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }

    public void cancelReserve(Long reserveId) {
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(EntityNotFoundException::new);

        reserve.cancelReserve(); //update

    }

    public void deleteReserve(Long reserveId) {

        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(EntityNotFoundException::new);

        reserveRepository.delete(reserve);
    }
}
