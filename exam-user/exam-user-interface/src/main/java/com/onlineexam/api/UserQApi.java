package com.onlineexam.api;

import com.onlineexam.bean.ResultBean;
import com.onlineexam.pojo.UserQ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserQApi {

    /**
     * 根据用户名和密码查询
     * @param userQname
     * @param password
     * @return
     */
    @GetMapping("userQ/loginQ")
    public ResultBean<UserQ> loginQ(@RequestParam("userQname") String userQname, @RequestParam("password") String password);

    /**
     * 通过id查询
     *
     * @param userQId
     * @return
     */
    @GetMapping("userQ/findUserQById")
    public ResultBean<UserQ> findUserQById(@RequestParam("userQId") Long userQId);

    /**
     * 通过登录账户查询列表
     *
     * @param userQAccount
     * @return
     */
    @GetMapping("userQ/findUserQsByuserQAccount")
    public ResultBean<List<UserQ>> findUserQsByUserQName(@RequestParam("userQAccount") String userQAccount);
}
