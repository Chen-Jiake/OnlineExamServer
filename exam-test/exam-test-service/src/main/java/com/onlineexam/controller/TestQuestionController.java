package com.onlineexam.controller;

import com.onlineexam.pojo.Question;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("testQuestion")
public class TestQuestionController {

    @Autowired
    private TestQuestionService testQuestionService;

    /**
     * 根据试卷id和用户id查询出该试卷下的所有试题，且按试题类型和难易程度排序
     * @param testId
     * @return
     */
    @GetMapping("findQuestionsByTestIdAndUserQId")
    public ResultBean<List<List<Question>>> findQuestionsByTestIdAndUserQId(@RequestParam("testId") Long testId, @RequestParam("userQId") Long userQId){
        List<List<Question>> list = testQuestionService.findQuestionsByTestIdAndUserQId(testId, userQId);
        if(list == null){
            return new ResultBean<>(600, "抱歉，您暂没有权限参加该考试！", null);
        }
        return new ResultBean<>(200, "查询成功！", list);
    }
}
