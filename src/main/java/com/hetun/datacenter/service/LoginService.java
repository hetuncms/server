package com.hetun.datacenter.service;

import com.hetun.datacenter.bean.LoginBean;
import com.hetun.datacenter.repository.LoginRepository;
import com.hetun.datacenter.tools.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    LoginRepository loginMapper;
    @Autowired
    public LoginService(LoginRepository loginMapper) {
        this.loginMapper = loginMapper;
    }

    public String login(LoginBean requestLoginBean) {
        List<LoginBean> all = loginMapper.findAll();
        LoginBean loginBean = all.get(0);
        if (loginBean.equals(requestLoginBean)) {
            return JWTUtils.getToken(loginBean);
        }

        return "";
    }
}
