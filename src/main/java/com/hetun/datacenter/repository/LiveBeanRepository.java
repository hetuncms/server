package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.LiveItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface LiveBeanRepository extends JpaRepository<LiveItem, Integer> {
    @Query(value = "select * from live_table  where live_type = :type order by top,match_start_time desc", nativeQuery = true)
    Page<LiveItem> findAllBySport(Pageable pageable,Integer type);


    @Query(value = "select * from live_table  where live_type = :type and (floor(extract(epoch from now()))<live_table.match_start_time or liveing) " +
            "order by top desc,liveing desc,match_start_time", nativeQuery = true)
    Page<LiveItem> findAllBySportUp(Pageable pageable,Integer type);

    @Query(value = "select * from live_table  where id = :id order by top desc,match_start_time", nativeQuery = true)
    LiveItem findAllById( Long id);


    @Query(value = "select * from live_table  where id = :matchId", nativeQuery = true)
    LiveItem findByMatchId(Integer matchId);

    @Modifying
    @Query(value = "update live_table set old = true", nativeQuery = true)
    Integer setAllItemIsOld();

    @Modifying
    @Query(value = "DELETE FROM live_table WHERE old!=false", nativeQuery = true)
    Integer deleteAllByOld();
    @Query(value = "select * from live_table where floor(extract(epoch from now()))<live_table.match_start_time or liveing order by top desc,liveing desc,match_start_time", nativeQuery = true)
    Page<LiveItem> findAllUp(PageRequest of);

    LiveItem findByLiveId(String liveid);

    @Query(value = "select * from live_table WHERE old=true", nativeQuery = true)
    LiveItem findAllByLiveing();
}
