package com.onlineexam.aoplog;

import com.onlineexam.bean.ResultBean;
import com.onlineexam.config.JwtProperties;
import com.onlineexam.feign.UserClient;
import com.onlineexam.feign.UserQClient;
import com.onlineexam.mapper.InfoMapper;
import com.onlineexam.pojo.UserInfo;
import com.onlineexam.utils.CookieUtils;
import com.onlineexam.utils.JwtUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Aspect
@EnableConfigurationProperties(JwtProperties.class)
public class LogAop {

    private String userName;    //访问用户

    private Date visitTime;//操作时间

    private String ip;  //访问ip

    private String _uri; //执行的类与方法

    private String describe;    //描述

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;

    @Autowired
    private UserQClient userQClient;

    @Autowired
    private InfoMapper infoMapper;

    private List<String> pathsList = new ArrayList<String>(Arrays.asList("/user/addUser", "/user/updateUser", "/user/delUserById",
            "/userQ/addUserQ", "/userQ/updateUserQ", "/userQ/delUserQById",
            "/role/addRole", "/role/updateRole", "/role/delRoleById", "/roleRights/updateRights",
            "/question/upload", "/question/downloadExcel", "/question/updateQuestion", "/question/delQuestionById", "/test/addTest",
            "/grade/addGrade", "/grade/updateGrade", "/grade/delGradeById",
            "/subject/addSubject", "/subject/updateSubject", "/subject/delSubjectById",
            "/auth/login", "/auth/logout", "/auth/loginQ", "/auth/logoutQ"));

    /**
     * 后置通知
     *
     * @param jp
     */
    @AfterReturning(returning = "result", pointcut = "execution(* com.onlineexam.controller.*.*(..))")
    public void doAfter(JoinPoint jp, Object result) {

        String uri = request.getRequestURI().toString();

        if (pathsList.contains(uri)){

            ResultBean rb = (ResultBean)result;

            if(rb.getHttpCode() < 400){ //只对成功操作进行记录
                visitTime = new Date();

                String className = jp.getTarget().getClass().getName();
                String methodName = jp.getSignature().getName();
                _uri = uri;

                //获取访问的ip
                ip = request.getRemoteAddr();

                //后台用户登录
                if("/auth/login".equals(uri)){
                    Object[] args = jp.getArgs();
                    String tmp_username = (String)args[2];

                    userName = "[后台]" + userClient.findUsersByUserName(tmp_username).getData().get(0).getUserRealname();
                    describe = rb.getMessage();
                    infoMapper.addInfo(userName, visitTime, ip, _uri, describe);
                    return;
                }

                //前台用户登录
                if("/auth/loginQ".equals(uri)){
                    Object[] args = jp.getArgs();
                    String tmp_username = (String)args[2];

                    userName = "[前台]" + userQClient.findUserQsByUserQName(tmp_username).getData().get(0).getUserQRealname();
                    describe = rb.getMessage();
                    infoMapper.addInfo(userName, visitTime, ip, _uri, describe);
                    return;
                }

                //前台用户退出
                if("/auth/logoutQ".equals(uri)){
                    try {
                        //获取前台用户
                        String q_token = CookieUtils.getCookieValue(request, "Q_TOKEN");
                        UserInfo userInfo = JwtUtils.getInfoFromToken(q_token, jwtProperties.getPublicKey());
                        userName = "[前台]" + userInfo.getUserRealname();
                    } catch (Exception e) {
                        userName = "{用户获取失败}";
                    }

                    describe = rb.getMessage();

                    //封装实体类
                    infoMapper.addInfo(userName, visitTime, ip, _uri, describe);
                    return;
                }

                try {
                    //获取后台用户
                    String token = CookieUtils.getCookieValue(request, "TOKEN");
                    UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
                    userName = "[后台]" + userInfo.getUserRealname();
                } catch (Exception e) {
                    userName = "{用户获取失败}";
                }

                describe = rb.getMessage();

                //封装实体类
                infoMapper.addInfo(userName, visitTime, ip, _uri, describe);
            }

        }
    }
}