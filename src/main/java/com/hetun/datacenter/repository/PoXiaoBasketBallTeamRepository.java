package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.PoXiaoZiJieBasketBallTeamBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PoXiaoBasketBallTeamRepository extends JpaRepository<PoXiaoZiJieBasketBallTeamBean.Result, Integer> {
}
