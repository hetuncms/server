package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.PredictionsBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionsRepository extends JpaRepository<PredictionsBean, Integer> {
}
