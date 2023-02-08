package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.RateOddsBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateOddsRepository extends JpaRepository<RateOddsBean.Result, Integer> {

    @Query(value = "select * from rate_odds where match_id = :matchId",nativeQuery = true)
    List<RateOddsBean.Result> findAllByMatchId(@Param("matchId") Integer matchId);
}
