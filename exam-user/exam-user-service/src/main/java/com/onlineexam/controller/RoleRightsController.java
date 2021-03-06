package com.onlineexam.controller;

import com.onlineexam.bean.ResultBean;
import com.onlineexam.service.RoleRightsService;
import com.onlineexam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roleRights")
public class RoleRightsController {

    @Autowired
    private RoleRightsService roleRightsService;

    @Autowired
    private RoleService roleService;

    /**
     * 根据角色id查询出该角色下的所有三级菜单ids供树形控件显示
     * @param fkRoleId
     * @return
     */
    @GetMapping("getRightsIdsByRoleId")
    public ResultBean<List<Long>> getRightsIdsByRoleId(@RequestParam("fkRoleId") Long fkRoleId){

        List<Long> list = roleRightsService.getRightsIdsByRoleId(fkRoleId);
        return new ResultBean<>(200, "查询成功！", list);
    }

    /**
     * 根据角色id查询出该角色下的所有一级和二级菜单用于首页菜单显示
     * @param fkRoleId
     * @return
     */
//    @GetMapping("getRightsByRoleId")
//    public ResultBean<List<Rights>> getRightsByRoleId(@RequestParam("fkRoleId") Long fkRoleId){
//        List<Rights> list = roleRightsService.getRightsByRoleId(fkRoleId);
//        return new ResultBean<>(200, "查询成功！", list);
//    }

    /**
     * 更新用户对应的权限菜单
     * @param rightsList
     * @return
     */
    @PutMapping("updateRights")
    public ResultBean<Void> updateRights(@RequestParam("fkRoleId") Long fkRoleId, @RequestParam("rightsList") List<Long> rightsList){
        roleRightsService.updateRights(fkRoleId, rightsList);
        return new ResultBean<>(201, "修改成功！", null);
    }
}
