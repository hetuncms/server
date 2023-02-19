package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.PredictionsBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PredictionsRepository extends JpaRepository<PredictionsBean, Integer> {
    @Query(value = "select u from PredictionsBean u where cast(u.startTime as DATE) = :select_date")
    Page<PredictionsBean> findAllByStartTime(Date select_date, Pageable pageable);

    @Query(value = "select distinct cast(start_time as DATE) from predictions order by start_time",nativeQuery = true)
    List<Date> findAllStartTime();
}
