package com.hetun.datacenter.tripartite.repository;

import com.hetun.datacenter.tripartite.bean.FootballLeagueBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<FootballLeagueBean.Result,Integer> {
}
