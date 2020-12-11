package com.onlineexam.service;

import com.onlineexam.pojo.TestUserQ;
import com.onlineexam.pojo.Question;
import com.onlineexam.pojo.UserQQuestion;
import com.onlineexam.mapper.UserQQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserQQuestionService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserQQuestionMapper userQQuestionMapper;

    @Autowired
    private TestUserQService testUserQService;

    /**
     * 用户提交试卷
     * @param questions
     * @param testId
     * @param userQId
     */
    public void commitTest(List<List<Question>> questions, Long testId, Long userQId) {
        int sum = 0;    //学生总得分

        for (List<Question> list: questions){
            for (Question t : list){
                if(questionService.findQuestionById(t.getQuestionId()).getQuestionYesanswer().equals(t.getQuestionYesanswer())){
                    sum += t.getQuestionScore();
                }
                UserQQuestion ut = new UserQQuestion();
                ut.setUserQQuestionUseranswer(t.getQuestionYesanswer());
                ut.setFkQuestionId(t.getQuestionId());
                ut.setFkTestId(testId);
                ut.setFkUserQId(userQId);

                userQQuestionMapper.insertSelective(ut);
            }
        }

        TestUserQ tu = testUserQService.findTestUserQByTestIdAndUserQId(testId, userQId);
        tu.setTestUserQLasttime(0);
        tu.setTestUserQCommittime(new Date());
        tu.setTestUserQScore(sum);

        System.out.println(tu);
        testUserQService.updateTestUserQ(tu);
    }

    public List<UserQQuestion> findUserQQuestionByTestIdAndUserQId(Long fkTestId, Long fkUserQId) {
        Example example = new Example(UserQQuestion.class);

        Example.Criteria criteria = example.createCriteria();

        if(fkTestId != null){
            criteria.andEqualTo("fkTestId", fkTestId);
        }

        if(fkUserQId != null){
            criteria.andEqualTo("fkUserQId", fkUserQId);
        }

        List<UserQQuestion> list = userQQuestionMapper.selectByExample(example);

        for(UserQQuestion ut : list){
            ut.setFkQuestion(questionService.findQuestionById(ut.getFkQuestionId()));
        }
        return list;
    }
}
