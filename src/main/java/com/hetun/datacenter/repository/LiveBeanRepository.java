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

import java.util.Date;
import java.util.List;

@Transactional
@Repository
public interface LiveBeanRepository extends JpaRepository<LiveItem, Integer> {
    @Query(value = "select * from live_table  where live_type = :type order by top,match_start_time desc", nativeQuery = true)
    Page<LiveItem> findAllBySport(Pageable pageable,Integer type);


    @Query(value = "select lt.*,ml.name_zh_abbr from live_table lt " +
            "left join match_league ml on lt.league_id=ml.id where lt.live_type = :type and (now()>lt" +
            ".match_start_time or lt.liveing) " +
            "order by lt.top desc,lt.liveing desc,lt.match_start_time", nativeQuery = true)
    Page<LiveItem> findAllBySportPager(Pageable pageable, Integer type);


    @Query(value = "select lt.*,ml.name_zh_abbr from live_table lt " +
            "left join match_league ml on lt.league_id=ml.id where lt.live_type = :type and cast(lt.match_start_time as DATE) = :startDate " +
            "and (now()>lt" +
            ".match_start_time or lt.liveing) " +
            "order by lt.top desc,lt.liveing desc,lt.match_start_time", nativeQuery = true)
    Page<LiveItem> findAllBySportByDatePager(Pageable pageable,Date startDate,Integer type);

    @Query(value = "select * from live_table  where live_type = " +
            ":type and cast(lt.startTime as DATE) = :select_date and (floor(extract(epoch from " +
            "now()))<live_table.match_start_time or liveing) " +
            "order by top desc,liveing desc,match_start_time", nativeQuery = true)
    Page<LiveItem> findAllBySportPager(Pageable pageable, Integer type,Date select_date);

    @Query(value = "select * from live_table  where live_type = :type and (floor(extract(epoch from now()))<live_table.match_start_time or liveing) " +
            "order by top desc,liveing desc,match_start_time", nativeQuery = true)
    List<LiveItem> findAllBySportAll(Integer type);
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
//    @Query(value = "select * from live_table where floor(extract(epoch from now()))<live_table.match_start_time or liveing order by top desc,liveing desc,match_start_time", nativeQuery = true)
    @Query(value = "select lt.*,ml.name_zh_abbr from live_table lt left join match_league ml on lt.league_id=ml.id order by lt.top desc,lt" +
            ".liveing desc,lt.match_start_time",
            nativeQuery = true)
    Page<LiveItem> findAllUp(PageRequest of);

    @Query(value = "select lt.*,ml.name_zh_abbr from live_table lt left join match_league ml on lt.league_id=ml.id " +
            "where cast(lt.match_start_time as DATE) = :select_date" +
            " order by lt.top desc,lt.liveing desc,lt.match_start_time",
            nativeQuery = true)
    Page<LiveItem> findAllUp(PageRequest of, Date select_date);

    LiveItem findByLiveId(String liveid);

    @Query(value = "select * from live_table WHERE old=true", nativeQuery = true)
    LiveItem findAllByLiveing();
    @Query(value = "select distinct cast(match_start_time as DATE) from live_table order by match_start_time",nativeQuery = true)
    List<Date> getAllDate();

    @Query(value = "select distinct cast(match_start_time as DATE) from live_table where live_type=:type order by match_start_time",nativeQuery = true)
    List<Date> getAllDate(Integer type);
}
