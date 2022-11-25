package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.MainLiveBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripartiteLiveBeanRepository extends JpaRepository<MainLiveBean,Integer> {
    @Query(value = "select * from tripartite_live_bean where sport = :type",nativeQuery = true)
    Page<MainLiveBean> findAllBySport(Pageable pageable, @Param("type") String type);

    @Override
    <S extends MainLiveBean> S save(S entity);

    Page<MainLiveBean> findAll(Pageable pageable);
}
