package com.hetun.datacenter.tripartite.repository;

import com.hetun.datacenter.tripartite.bean.LeagueBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueBean.Result,Integer> {
}
