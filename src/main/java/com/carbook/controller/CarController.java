package com.carbook.controller;

import com.carbook.dto.CarFormDto;
import com.carbook.dto.CarSearchDto;
import com.carbook.dto.MainCarDto;
import com.carbook.entity.Car;
import com.carbook.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    //등록페이지
    @GetMapping(value = "/admin/write")
    public String carWrite(Model model) {
        model.addAttribute("carFormDto", new CarFormDto());
        return "car/carWrite";
    }

    //등록처리
    @PostMapping(value = "/admin/write/new")
    public String carNew(@Valid CarFormDto carFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("carImgFile") List<MultipartFile> carImgFileList) {

        if(bindingResult.hasErrors()) return "car/carWrite";

        if(carImgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage",
                    "한개 이상의 이미지는 필수 입력입니다.");
            return "car/carWrite";
        }

        try {
            carService.saveCar(carFormDto, carImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "에러가 발생했습니다.");
            return "car/carWrite";
        }

        return "redirect:/car/list";
    }

    //수정페이지
    @GetMapping(value = "/admin/rewrite/{carId}")
    public String carDtl(@PathVariable("carId") Long carId, Model model) {
        try {
            CarFormDto carFormDto = carService.getCarDtl(carId);
            model.addAttribute("carFormDto", carFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "에러가 발생했습니다.");

            model.addAttribute("carFormDto", new CarFormDto());
            return "car/carWrite";
        }

        return "car/carRewrite";
    }

    //수정처리
    @PostMapping(value = "/admin/rewrite/{carId}")
    public String carUpdate(@Valid CarFormDto carFormDto, Model model, BindingResult bindingResult,
                             @RequestParam("carImgFile") List<MultipartFile> carImgFileList,
                             @PathVariable("carId") Long carId) {

        if(bindingResult.hasErrors()) return "car/carWrite";

        CarFormDto getCarFormDto = carService.getCarDtl(carId);


        if(carImgFileList.get(0).isEmpty() && carFormDto.getId() == null) {
            model.addAttribute("errorMessage",
                    "한개 이상의 이미지는 필수 입력입니다.");
            model.addAttribute("carFormDto", getCarFormDto);
            return "car/carRewrite";
        }

        try {
            carService.updateCar(carFormDto, carImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "수정중 에러가 발생했습니다.");
            model.addAttribute("carFormDto", getCarFormDto);
            return "car/carRewrite";
        }

        return "redirect:/";

    }

    //관리페이지
    @GetMapping(value = {"/admin/cars", "/admin/cars/{page}"})
    public String carManage(CarSearchDto carSearchDto,
                             @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 3);

        Page<Car> cars = carService.getAdminCarPage(carSearchDto, pageable);

        model.addAttribute("cars", cars);
        model.addAttribute("carSearchDto", carSearchDto);


        model.addAttribute("maxPage", 5);

        return "car/carMng";
    }

    //목록
    @GetMapping(value = "/car/list")
    public String carList(Model model, CarSearchDto carSearchDto,
                               @RequestParam(value = "page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 12);
        Page<MainCarDto> cars = carService.getMainCarPage(carSearchDto, pageable);

        model.addAttribute("cars", cars);
        model.addAttribute("carSearchDto", carSearchDto);
        model.addAttribute("maxPage", 5);

        return "car/carList";
    }

    //상세페이지
    @GetMapping(value = "/car/{carId}")
    public String carDtl(Model model, @PathVariable("carId") Long carId) {
        CarFormDto carFormDto= carService.getCarDtl(carId);
        model.addAttribute("car", carFormDto);
        return "car/carDtl";
    }
}
