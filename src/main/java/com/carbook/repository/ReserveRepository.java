package com.carbook.repository;

import com.carbook.entity.Reserve;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("select o from Reserve o where o.member.email = :email order by o.reserveDate desc")
    List<Reserve> findReserves(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Reserve o where o.member.email = :email")
    Long countReserve(@Param("email") String email);
}
