package com.onlineexam.api;

import com.onlineexam.bean.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoleRightsApi {

    /**
     * 根据角色id查询出该角色下的所有三级菜单ids
     * @param fkRoleId
     * @return
     */
    @GetMapping("roleRights/getRightsIdsByRoleId")
    public ResultBean<List<Long>> getRightsIdsByRoleId(@RequestParam("fkRoleId") Long fkRoleId);
}
