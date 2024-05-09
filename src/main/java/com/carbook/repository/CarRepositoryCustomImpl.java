package com.carbook.repository;

import com.carbook.constant.CarSellStatus;
import com.carbook.dto.CarSearchDto;
import com.carbook.dto.MainCarDto;
import com.carbook.dto.QMainCarDto;
import com.carbook.entity.Car;
import com.carbook.entity.QCar;
import com.carbook.entity.QCarImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class CarRepositoryCustomImpl implements CarRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public CarRepositoryCustomImpl(EntityManager em) {

        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QCar.car.regTime.after(dateTime);
    }

    private BooleanExpression searchSellStatusEq(CarSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QCar.car.carSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("carNm", searchBy)) {
            return QCar.car.carNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) {
            return QCar.car.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }
    @Override
    public Page<Car> getAdminCarPage(CarSearchDto carSearchDto, Pageable pageable) {
        List<Car> content = queryFactory
                .selectFrom(QCar.car)
                .where(regDtsAfter(carSearchDto.getSearchDateType()),
                        searchSellStatusEq(carSearchDto.getSearchSellStatus()),
                        searchByLike(carSearchDto.getSearchBy(), carSearchDto.getSearchQuery()))
                .orderBy(QCar.car.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QCar.car)
                .where(regDtsAfter(carSearchDto.getSearchDateType()),
                        searchSellStatusEq(carSearchDto.getSearchSellStatus()),
                        searchByLike(carSearchDto.getSearchBy(), carSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private  BooleanExpression carNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)  ? null : QCar.car.carNm.like("%"+searchQuery+"%");
    }
    @Override
    public Page<MainCarDto> getMainCarPage(CarSearchDto carSearchDto, Pageable pageable) {
        QCar car = QCar.car;
        QCarImg carImg = QCarImg.carImg;

        List<MainCarDto> content = queryFactory
                .select(
                        new QMainCarDto(
                                car.id,
                                car.carNm,
                                car.carDetail,
                                carImg.imgUrl,
                                car.price)
                )
                .from(carImg)
                .join(carImg.car, car)
                .where(carImg.repImgYn.eq("Y"))
                .where(carNmLike(carSearchDto.getSearchQuery()))
                .orderBy(car.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(carImg)
                .join(carImg.car, car)
                .where(carImg.repImgYn.eq("Y"))
                .where(carNmLike(carSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
