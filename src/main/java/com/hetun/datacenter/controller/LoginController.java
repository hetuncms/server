package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LoginBean;
import com.hetun.datacenter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class LoginController {

    LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public BaseBean<String> login(@RequestBody LoginBean loginBean){
        String token = loginService.login(loginBean);
        if (StringUtils.hasText(token)) {
            return new BaseBean.Builder().build(token);
        }
        return new BaseBean.Builder().buildError("登录失败");
    }

    @GetMapping("/info")
    @CrossOrigin
    public String getInfo() {
        return "{\"code\":20000,\"data\":{\"name\":\"admin\",\"avatar\":\"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif\"}}";
    }
}
