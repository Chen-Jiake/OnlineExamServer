package com.onlineexam.controller;

import com.onlineexam.bean.ResultBean;
import com.onlineexam.pojo.Rights;
import com.onlineexam.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rights")
public class RightsController {

    @Autowired
    private RightsService rightsService;

    /**
     * 查询所有菜单
     * @return
     */
    @GetMapping("findAll")
    public ResultBean<List<Rights>> findAll(){
        List<Rights> list = rightsService.findAll();
        return new ResultBean<>(200, "查询成功！", list);
    }

    /**
     * 根据id查询一个权限
     * @param rightsId
     * @return
     */
    @GetMapping("findRightsById")
    public ResultBean<Rights> findRightsById(@RequestParam("rightsId") Long rightsId){
        Rights rights = rightsService.findRightsById(rightsId);
        return new ResultBean<Rights>(200, "查询成功", rights);
    }
}
