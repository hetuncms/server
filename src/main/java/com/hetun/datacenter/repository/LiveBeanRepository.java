package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.LiveItem;
import org.apache.ibatis.annotations.Param;
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
public interface LiveBeanRepository extends JpaRepository<LiveItem, Long> {
    @Query(value = "select * from live_table  where live_type = :type order by is_top,long_time desc", nativeQuery = true)
    Page<LiveItem> findAllBySport(Pageable pageable, @Param("type") Integer type);

    @Override
    <S extends LiveItem> S save(S entity);

    @Query(value = "select * from live_table  where live_type = :type and (live_status=true or long_time>floor(extract(epoch from now())*1000)) order by is_top desc,long_time", nativeQuery = true)
    Page<LiveItem> findAllBySportUp(Pageable pageable, @Param("type") Integer type);


    @Query(value = "select * from live_table  where live_id = :liveId order by is_top desc,long_time", nativeQuery = true)
    Page<LiveItem> findAll(Pageable pageable);


    @Query(value = "select * from live_table  where id = :id order by is_top desc,long_time", nativeQuery = true)
    LiveItem findAllById(@Param("type") Long id);

    @Query(value = "select * from live_table  where live_id = :liveId order by is_top desc,long_time", nativeQuery = true)
    LiveItem findAllByLiveId(@Param("liveId") String liveId);

    @Modifying
    @Query(value = "update live_table set is_old = true", nativeQuery = true)
    void setAllItemIsOld();

    @Modifying
    @Query(value = "DELETE FROM live_table WHERE is_old=true", nativeQuery = true)
    void deleteAllByOld();
    @Query(value = "select * from live_table  where live_status=true or long_time>floor(extract(epoch from now())*1000) order by is_top desc,long_time", nativeQuery = true)
    Page<LiveItem> findAllUp(PageRequest of);

    LiveItem findByLiveId(String liveid);
}
