package com.hetun.datacenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hetun.datacenter.bean.LoginBean;
import com.hetun.datacenter.mapper.LoginMapper;
import com.hetun.datacenter.tools.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    LoginMapper loginMapper;

    @Autowired
    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public String login(LoginBean requestLoginBean) {
        LoginBean loginBean = loginMapper.selectOne(new QueryWrapper<>());
        if (loginBean.equals(requestLoginBean)) {
            return JWTUtils.getToken(loginBean);
        }

        return "";
    }
}
