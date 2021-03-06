package com.onlineexam.controller;

import com.onlineexam.bean.PageResult;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.pojo.Rights;
import com.onlineexam.pojo.Role;
import com.onlineexam.pojo.User;
import com.onlineexam.service.RoleRightsService;
import com.onlineexam.service.RoleService;
import com.onlineexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRightsService roleRightsService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addUser")
    public ResultBean<Void> addUser(@RequestBody @Valid User user) {
        List<User> list = userService.findUsersByUserAccount(user.getUserAccount());
        if (list.size() >= 1) {
            return new ResultBean(600, "该登录账户已存在！", null);
        }
        List<User> list2 = userService.findUsersByRealname(user.getUserRealname());
        if (list2.size() >= 1) {
            return new ResultBean(600, "该真实姓名已被使用！", null);
        }
        userService.addUser(user);
        return new ResultBean(201, "后台用户["+ user.getUserRealname() +"]添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param userId
     * @return
     */
    @DeleteMapping("delUserById")
    public ResultBean<Void> delUserById(@RequestParam("userId") Long userId) {
        try {
            String realName = userService.findUserById(userId).getUserRealname();
            userService.delUserById(userId);
            return new ResultBean(204, "后台用户["+ realName +"]删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该用户可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public ResultBean<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResultBean(201, "后台用户["+ user.getUserRealname() +"]修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param userId
     * @return
     */
    @GetMapping("findUserById")
    public ResultBean<User> findUserById(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResultBean(600, "用户不存在！", null);
        }
        return new ResultBean(200, "查询成功！", user);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findUsers")
    public ResultBean<List<User>> findUsers() {
        List<User> list = userService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param userAccount
     * @param userRealname
     * @param fkUserRoleId
     * @return
     */
    @GetMapping("findUsersByPage")
    public ResultBean<PageResult<User>> findUsersByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "userAccount", required = false) String userAccount,
            @RequestParam(value = "userRealname", required = false) String userRealname,
            @RequestParam(value = "fkUserRoleId", required = false) Integer fkUserRoleId) {
        PageResult<User> usersByPage = userService.findUsersByPage(page, rows, userAccount, userRealname, fkUserRoleId);
        return new ResultBean(200, "查询成功！", usersByPage);
    }


    /**
     * 通过登录账户查询列表
     *
     * @param userAccount
     * @return
     */
    @GetMapping("findUsersByuserAccount")
    public ResultBean<List<User>> findUsersByUserName(@RequestParam("userAccount") String userAccount) {
        return new ResultBean(200, "查询成功！", userService.findUsersByUserAccount(userAccount));
    }

    /**
     * 通过真实姓名查询列表
     *
     * @param userRealname
     * @return
     */
    @GetMapping("findUsersByUserRealname")
    public ResultBean<List<User>> findUsersByUserRealname(@RequestParam("userRealname") String userRealname) {
        return new ResultBean(200, "查询成功！", userService.findUsersByRealname(userRealname));
    }

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @GetMapping("login")
    public ResultBean<User> login(@RequestParam("username") String username, @RequestParam("password") String password){
        User user = userService.login(username, password);
        if(user == null){
            return new ResultBean<User>(600, "用户名或密码错误！", null);
        }
        return new ResultBean<User>(200, "用户获取成功！", user);
    }

    /**
     * 根据用户id查询角色,再根据角色id查询其下的所有一级二级菜单
     * @param userId
     * @return
     */
    @GetMapping("findRolesRightsByUserId")
    public ResultBean<List<Rights>> findRolesRightsByUserId(@RequestParam("userId") Long userId){
        User user = userService.findUserById(userId);
        Role role = roleService.findRoleById(user.getFkUserRoleId());
        List<Rights> list = roleRightsService.getRightsByRoleId(role.getRoleId());
        return new ResultBean<>(200, "查询成功", list);
    }
}
