package com.hetun.datacenter.repository;

import com.hetun.datacenter.tripartite.bean.LeagueBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PoXiaoLeagueRepository extends JpaRepository<LeagueBean.LeagueResult, Integer> {

}
