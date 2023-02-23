package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.PoXiaoZiJieLiveInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PoXiaoLiveInfoRepository extends JpaRepository<PoXiaoZiJieLiveInfoBean.Result, Integer> {

    @Query(value = "select * from live_info  where match_id = :matchId", nativeQuery = true)
    PoXiaoZiJieLiveInfoBean.Result findByMatchId(@Param("matchId") Integer matchId);


    @Modifying
    @Query(value = "update live_info set is_old = true", nativeQuery = true)
    void setAllItemIsOld();

    @Modifying
    @Query(value = "DELETE FROM live_info WHERE is_old=true", nativeQuery = true)
    void deleteAllByOld();


}
