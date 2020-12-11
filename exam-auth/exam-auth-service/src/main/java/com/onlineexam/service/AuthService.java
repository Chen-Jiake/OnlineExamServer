package com.onlineexam.service;

import com.onlineexam.bean.ResultBean;
import com.onlineexam.config.JwtProperties;
import com.onlineexam.feign.UserClient;
import com.onlineexam.feign.UserQClient;
import com.onlineexam.pojo.User;
import com.onlineexam.pojo.UserInfo;
import com.onlineexam.pojo.UserQ;
import com.onlineexam.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;  //处理后台用户

    @Autowired
    private UserQClient userQClient;    //处理前台用户

    @Autowired
    private JwtProperties jwtProperties;

    public String login(String username, String password) {
        //根据用户名密码查询
        ResultBean<User> res = userClient.login(username, password);

        if(res.getData() == null){
            return null;
        }
        User user = res.getData();
        try {
            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(user.getUserId());
            userInfo.setUserRealname(user.getUserRealname());

            //生成tioken
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String loginQ(String username, String password) {
        //根据用户名密码查询
        ResultBean<UserQ> res = userQClient.loginQ(username, password);

        if(res.getData() == null){
            return null;
        }
        UserQ userQ = res.getData();
        try {
            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(userQ.getUserQId());
            userInfo.setUserRealname(userQ.getUserQRealname());

            //生成tioken
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
