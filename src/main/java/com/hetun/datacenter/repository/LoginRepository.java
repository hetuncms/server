package com.hetun.datacenter.repository;

import com.hetun.datacenter.bean.LoginBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface LoginRepository extends JpaRepository<LoginBean, Long> {

}
