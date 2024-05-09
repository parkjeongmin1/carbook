package com.carbook.repository;

import com.carbook.dto.CarSearchDto;
import com.carbook.dto.MainCarDto;
import com.carbook.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarRepositoryCustom {
   Page<Car> getAdminCarPage(CarSearchDto carSearchDto, Pageable pageable);

   Page<MainCarDto> getMainCarPage(CarSearchDto carSearchDto, Pageable pageable);
}
