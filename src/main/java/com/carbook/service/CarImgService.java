package com.carbook.service;

import com.carbook.entity.CarImg;
import com.carbook.repository.CarImgRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CarImgService {

    @Value("${carImgLocation}")
    private String carImgLocation;

    private final CarImgRepository carImgRepository;
    private final FileService fileService;

    //이미지저장
    public void saveCarImg(CarImg carImg, MultipartFile carImgFile) throws Exception{
        String oriImgName = carImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(carImgLocation,
                    oriImgName, carImgFile.getBytes());

            imgUrl = "/images/car/" + imgName;
        }

        carImg.updateCarImg(oriImgName, imgName, imgUrl);
        carImgRepository.save(carImg);
    }

    //이미지수정
    public void updateCarImg(Long carImgId, MultipartFile carImgFile) throws Exception {
        if(!carImgFile.isEmpty()) {

            CarImg savedCarImg = carImgRepository.findById(carImgId)
                    .orElseThrow(EntityExistsException::new);

            if(!StringUtils.isEmpty(savedCarImg.getImgName())) {
                fileService.deleteFile(carImgLocation + "/" + savedCarImg.getImgName());
            }

            String oriImgName = carImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(carImgLocation, oriImgName, carImgFile.getBytes());
            String imgUrl = "/images/car/" + imgName;

            savedCarImg.updateCarImg(oriImgName, imgName, imgUrl);

        }
    }
}
