package com.hetun.datacenter.tripartite.repository;

import com.hetun.datacenter.tripartite.bean.RateOddsCompanyBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateOddsCompanyRepository  extends JpaRepository<RateOddsCompanyBean.Result,Integer> {
}
