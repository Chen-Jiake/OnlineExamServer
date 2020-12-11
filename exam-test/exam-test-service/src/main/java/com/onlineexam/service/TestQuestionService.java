package com.onlineexam.service;

import com.onlineexam.pojo.TestQuestion;
import com.onlineexam.pojo.Question;
import com.onlineexam.feign.UserQClient;
import com.onlineexam.mapper.TestQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TestQuestionService {

    @Autowired
    private TestQuestionMapper testQuestionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserQClient userQClient;

    @Autowired
    private TestService testService;

    /**
     * 添加
     *
     * @param testQuestion
     */
    public void addQuestion(TestQuestion testQuestion) {
        testQuestionMapper.insertSelective(testQuestion);
    }

    /**
     * 根据试卷id查询出该试卷下的所有试题，且按试题类型和难易程度排序
     * @param testId
     * @return
     */
    public List<List<Question>> findQuestionsByTestIdAndUserQId(Long testId, Long userQId) {

        //查询test的gradeId和user的gradeId是否相同，不同则不能考试
        Long grade1 = testService.findTestById(testId).getFkTestGradeId();

        Long grade2 = userQClient.findUserQById(userQId).getData().getFkUserQGradeId();

        if(!grade1.equals(grade2)){
            return null;
        }

        Example example = new Example(TestQuestion.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkTestId", testId);

        List<TestQuestion> list = testQuestionMapper.selectByExample(example);

        List<Question> questionList = new ArrayList<>();

        for(TestQuestion tt : list){
            Question t = questionService.findQuestionById(tt.getFkQuestionId());
            questionList.add(t);
        }

        questionList.sort(new QuestionSortCompator());

        //正确答案清空
        for(Question t:questionList){
            t.setQuestionYesanswer("");
        }

        List<List<Question>> questions = new ArrayList<>();

        List<Question> radio = new ArrayList<>();
        List<Question> multi = new ArrayList<>();

        for(Question t: questionList){
            if(t.getQuestionType() == 0){
                radio.add(t);
            }
            if(t.getQuestionType() == 1){
                multi.add(t);
            }
        }

        //按类型分别归类存放
        questions.add(radio);
        questions.add(multi);

        return questions;
    }
}

class QuestionSortCompator implements Comparator<Question>{

    @Override
    public int compare(Question o1, Question o2) {
        return o1.getQuestionLevel() - o2.getQuestionLevel();
    }
}