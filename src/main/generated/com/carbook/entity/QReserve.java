package com.carbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserve is a Querydsl query type for Reserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserve extends EntityPathBase<Reserve> {

    private static final long serialVersionUID = 882909679L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserve reserve = new QReserve("reserve");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final ListPath<ReserveCar, QReserveCar> reserveCars = this.<ReserveCar, QReserveCar>createList("reserveCars", ReserveCar.class, QReserveCar.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> reserveDate = createDateTime("reserveDate", java.time.LocalDateTime.class);

    public final EnumPath<com.carbook.constant.ReserveStatus> reserveStatus = createEnum("reserveStatus", com.carbook.constant.ReserveStatus.class);

    public QReserve(String variable) {
        this(Reserve.class, forVariable(variable), INITS);
    }

    public QReserve(Path<? extends Reserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserve(PathMetadata metadata, PathInits inits) {
        this(Reserve.class, metadata, inits);
    }

    public QReserve(Class<? extends Reserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

