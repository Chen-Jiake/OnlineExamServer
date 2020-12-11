package com.onlineexam.controller;

import com.onlineexam.pojo.Question;
import com.onlineexam.pojo.UserQQuestion;
import com.onlineexam.pojo.vo.UserQQuestionQueryVo;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.service.UserQQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userQQuestion")
public class UserQQuestionController {

    @Autowired
    private UserQQuestionService userQQuestionService;

    /**
     * 提交试卷
     * @param vo
     * @return
     */
    @PostMapping("commitTest")
    public ResultBean<Void> commitTest(@RequestBody UserQQuestionQueryVo vo){
        List<List<Question>> questions = vo.getQuestionList();
        Long testId = vo.getTestId();
        Long userQId = vo.getUserQId();

        userQQuestionService.commitTest(questions, testId, userQId);
        return new ResultBean<>(204, "更新成功", null);
    }

    /**
     * 根据testId和userQId查询该用户答过的所有试题
     * @param fkTestId
     * @param fkUserQId
     * @return
     */
    @GetMapping("findUserQQuestionByTestIdAndUserQId")
    public ResultBean<List<UserQQuestion>> findUserQQuestionByTestIdAndUserQId(@RequestParam("fkTestId") Long fkTestId, @RequestParam("fkUserQId") Long fkUserQId){
        List<UserQQuestion> list = userQQuestionService.findUserQQuestionByTestIdAndUserQId(fkTestId, fkUserQId);
        return new ResultBean<>(200, "查询成功", list);
    }
}
