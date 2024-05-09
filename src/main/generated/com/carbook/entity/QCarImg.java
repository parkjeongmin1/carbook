package com.carbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarImg is a Querydsl query type for CarImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarImg extends EntityPathBase<CarImg> {

    private static final long serialVersionUID = -543254596L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarImg carImg = new QCarImg("carImg");

    public final QBase _super = new QBase(this);

    public final QCar car;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath oriImgName = createString("oriImgName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath repImgYn = createString("repImgYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QCarImg(String variable) {
        this(CarImg.class, forVariable(variable), INITS);
    }

    public QCarImg(Path<? extends CarImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarImg(PathMetadata metadata, PathInits inits) {
        this(CarImg.class, metadata, inits);
    }

    public QCarImg(Class<? extends CarImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCar(forProperty("car")) : null;
    }

}

