package com.carbook.service;

import com.carbook.dto.CarFormDto;
import com.carbook.dto.CarImgDto;
import com.carbook.dto.CarSearchDto;
import com.carbook.dto.MainCarDto;
import com.carbook.entity.Car;
import com.carbook.entity.CarImg;
import com.carbook.entity.Member;
import com.carbook.repository.CarImgRepository;
import com.carbook.repository.CarRepository;
import com.carbook.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final CarImgRepository carImgRepository;
    private final CarImgService carImgService;
    private final MemberRepository memberRepository;

    public Long saveCar(CarFormDto carFormDto,
                        List<MultipartFile> carImgFileList) throws Exception {

        Car car = carFormDto.creatCar();
        carRepository.save(car);


        for (int i = 0; i < carImgFileList.size(); i++) {
            CarImg carImg = new CarImg();
            carImg.setCar(car);

            if(i == 0) {
                carImg.setRepImgYn("Y");
            } else {
                carImg.setRepImgYn("N");
            }

            carImgService.saveCarImg(carImg, carImgFileList.get(i));
        }

        return car.getId();
    }

    @Transactional(readOnly = true)
    public CarFormDto getCarDtl(Long carId) {

        List<CarImg> carImgList =carImgRepository.findByCarIdOrderByIdAsc(carId);

        List<CarImgDto> carImgDtoList = new ArrayList<>();
        for (CarImg carImg : carImgList) {
            CarImgDto carImgDto = CarImgDto.of(carImg);
            carImgDtoList.add(carImgDto);
        }

        Car car = carRepository.findById(carId).orElseThrow(EntityNotFoundException::new);

        CarFormDto carFormDto = CarFormDto.of(car);

        carFormDto.setCarImgDtoList(carImgDtoList);

        return carFormDto;

    }

    //수정하기
    public Long updateCar(CarFormDto carFormDto,
                           List<MultipartFile> carImgFileList) throws Exception {

        Car car = carRepository.findById(carFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);


        car.updateCar(carFormDto);

        List<Long> carImgIds = carFormDto.getCarImgIds();

        for (int i = 0; i < carImgFileList.size(); i++) {

            carImgService.updateCarImg(carImgIds.get(i), carImgFileList.get(i));
        }
        return car.getId();
    }

    @Transactional(readOnly = true)
    public Page<Car> getAdminCarPage(CarSearchDto carSearchDto, Pageable pageable) {
        Page<Car> carPage = carRepository.getAdminCarPage(carSearchDto, pageable);
        return carPage;
    }

    public Page<MainCarDto> getMainCarPage(CarSearchDto carSearchDto, Pageable pageable) {
        Page<MainCarDto> mainCarDtoPage = carRepository.getMainCarPage(carSearchDto, pageable);

        return mainCarDtoPage;
    }

}
