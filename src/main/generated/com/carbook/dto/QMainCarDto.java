package com.carbook.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.carbook.dto.QMainCarDto is a Querydsl Projection type for MainCarDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainCarDto extends ConstructorExpression<MainCarDto> {

    private static final long serialVersionUID = -735899535L;

    public QMainCarDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> carNm, com.querydsl.core.types.Expression<String> carDetail, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price) {
        super(MainCarDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class}, id, carNm, carDetail, imgUrl, price);
    }

}

