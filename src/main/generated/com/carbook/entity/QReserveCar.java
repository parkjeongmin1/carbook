package com.carbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserveCar is a Querydsl query type for ReserveCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserveCar extends EntityPathBase<ReserveCar> {

    private static final long serialVersionUID = 382593893L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserveCar reserveCar = new QReserveCar("reserveCar");

    public final QCar car;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReserve reserve;

    public final NumberPath<Integer> reservePrice = createNumber("reservePrice", Integer.class);

    public QReserveCar(String variable) {
        this(ReserveCar.class, forVariable(variable), INITS);
    }

    public QReserveCar(Path<? extends ReserveCar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserveCar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserveCar(PathMetadata metadata, PathInits inits) {
        this(ReserveCar.class, metadata, inits);
    }

    public QReserveCar(Class<? extends ReserveCar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCar(forProperty("car")) : null;
        this.reserve = inits.isInitialized("reserve") ? new QReserve(forProperty("reserve"), inits.get("reserve")) : null;
    }

}

