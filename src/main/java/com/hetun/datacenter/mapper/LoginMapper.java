package com.hetun.datacenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hetun.datacenter.bean.LoginBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<LoginBean> {
}
