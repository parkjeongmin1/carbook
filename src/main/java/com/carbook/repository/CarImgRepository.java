package com.carbook.repository;

import com.carbook.entity.CarImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CarImgRepository extends JpaRepository<CarImg, Long> {
    List<CarImg> findByCarIdOrderByIdAsc(Long carId);

    CarImg findByCarIdAndRepImgYn(Long carId, String repImgYn);
}
