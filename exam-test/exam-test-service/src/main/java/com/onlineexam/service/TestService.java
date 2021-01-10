package com.onlineexam.service;

import com.onlineexam.mapper.TestUserQMapper;
import com.onlineexam.mapper.UserQQuestionMapper;
import com.onlineexam.pojo.Test;
import com.onlineexam.pojo.TestQuestion;
import com.onlineexam.pojo.TestUserQ;
import com.onlineexam.pojo.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineexam.bean.PageResult;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.enums.ExamExceptionEnum;
import com.onlineexam.exception.ExamException;
import com.onlineexam.feign.GradeClient;
import com.onlineexam.feign.SubjectClient;
import com.onlineexam.mapper.TestMapper;
import com.onlineexam.mapper.TestQuestionMapper;
import com.onlineexam.pojo.Grade;
import com.onlineexam.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SubjectClient subjectClient;

    @Autowired
    private GradeClient gradeClient;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestQuestionMapper testQuestionMapper;

    @Autowired
    private TestUserQMapper testUserQMapper;

    @Autowired
    private UserQQuestionMapper userQQuestionMapper;

    @Autowired
    private TestUserQService testUserQService;

    private double simplePercent;
    private double middlePercent;
    private double diffPercent;

    /**
     * 抽组试题生成试卷
     *
     * @param test
     */
    public void addTest(Test test, Integer level) {

        test.setTestJudgeSum(0);
        if(level == 0) {
            simplePercent = 0.5;
            middlePercent = 0.4;
            diffPercent = 0.1;
        } else if (level == 1) {
            simplePercent = 0.4;
            middlePercent = 0.4;
            diffPercent = 0.2;
        } else {
            simplePercent = 0.3;
            middlePercent = 0.4;
            diffPercent = 0.3;
        }

        int selectOne = test.getTestSelectOneSum();
        int selectMore = test.getTestSelectMoreSum();
        int judge = test.getTestJudgeSum();

        Double floor1 = Math.floor(selectOne * simplePercent);
        Double floor2 = Math.floor(selectOne * middlePercent);
        Double floor3 = Math.floor(selectOne * diffPercent);

        Double floor4 = Math.floor(selectMore * simplePercent);
        Double floor5 = Math.floor(selectMore * middlePercent);
        Double floor6 = Math.floor(selectMore * diffPercent);

        Double floor7 = Math.floor(judge * simplePercent);
        Double floor8 = Math.floor(judge * middlePercent);
        Double floor9 = Math.floor(judge * diffPercent);

        int onesimple = floor1.intValue(); //单选简单题数目
        int onemiddle = floor2.intValue(); //单选中等题数目
        int onediff = floor3.intValue(); //单选高难题数目

        int moresimple = floor4.intValue();
        int moremiddle = floor5.intValue();
        int morediff = floor6.intValue();

        int judgesimple = floor7.intValue();
        int judgemiddle = floor8.intValue();
        int judgediff = floor9.intValue();

        if(selectOne > (onesimple+onemiddle+onediff)){
            onesimple = selectOne - onemiddle - onediff;
        }

        if(selectMore > (moresimple+moremiddle+morediff)){
            moresimple = selectMore - moremiddle - morediff;
        }
        if(judge > (judgesimple+judgemiddle+judgediff)){
            judgesimple = judge - judgemiddle - judgediff;
        }

        List<Question> questions = questionService.findAll();

        List<Question> onesimpleList = new ArrayList<>();
        List<Question> onemiddleList = new ArrayList<>();
        List<Question> onediffList = new ArrayList<>();

        List<Question> moresimpleList = new ArrayList<>();
        List<Question> moremiddleList = new ArrayList<>();
        List<Question> morediffList = new ArrayList<>();

        List<Question> judgesimpleList = new ArrayList<>();
        List<Question> judgemiddleList = new ArrayList<>();
        List<Question> judgediffList = new ArrayList<>();

        //先将题库中所有符合条件的全部放入各自的list中
        if(!CollectionUtils.isEmpty(questions)){
            for (Question t : questions){
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 0 && t.getQuestionLevel() == 0){ //单选简单
                    onesimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 0 && t.getQuestionLevel() == 1){
                    onemiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 0 && t.getQuestionLevel() == 2){
                    onediffList.add(t);
                }

                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 1 && t.getQuestionLevel() == 0){
                    moresimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 1 && t.getQuestionLevel() == 1){
                    moremiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 1 && t.getQuestionLevel() == 2){
                    morediffList.add(t);
                }

                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 2 && t.getQuestionLevel() == 0){
                    judgesimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 2 && t.getQuestionLevel() == 1){
                    judgemiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getQuestionType() == 2 && t.getQuestionLevel() == 2){
                    judgediffList.add(t);
                }
            }
        }

        if(onesimpleList.size() < onesimple){
            throw new ExamException(ExamExceptionEnum.ONESIMPLE_LESS);
        }
        if(onemiddleList.size() < onemiddle){
            throw new ExamException(ExamExceptionEnum.ONEMIDDLE_LESS);
        }
        if(onediffList.size() < onediff){
            throw new ExamException(ExamExceptionEnum.ONEDIFF_LESS);
        }

        if(moresimpleList.size() < moresimple){
            throw new ExamException(ExamExceptionEnum.MORESIMPLE_LESS);
        }
        if(moremiddleList.size() < moremiddle){
            throw new ExamException(ExamExceptionEnum.MOREMIDDLE_LESS);
        }
        if(morediffList.size() < morediff){
            throw new ExamException(ExamExceptionEnum.MOREDIFF_LESS);
        }

        if(judgesimpleList.size() < judgesimple){
            throw new ExamException(ExamExceptionEnum.JUDGESIMPLE_LESS);
        }
        if(judgemiddleList.size() < judgemiddle){
            throw new ExamException(ExamExceptionEnum.JUDGEMIDDLE_LESS);
        }
        if(judgediffList.size() < judgediff){
            throw new ExamException(ExamExceptionEnum.JUDGEDIFF_LESS);
        }

        onesimpleList = getSubByRadom(onesimpleList, onesimple);
        onemiddleList = getSubByRadom(onemiddleList, onemiddle);
        onediffList = getSubByRadom(onediffList, onediff);

        moresimpleList = getSubByRadom(moresimpleList, moresimple);
        moremiddleList = getSubByRadom(moremiddleList, moremiddle);
        morediffList = getSubByRadom(morediffList, morediff);

        judgesimpleList = getSubByRadom(judgesimpleList, judgesimple);
        judgemiddleList = getSubByRadom(judgemiddleList, judgemiddle);
        judgediffList = getSubByRadom(judgediffList, judgediff);

        int sum = 0; //试卷总分

        test.setTestQuestionsum(selectOne + selectMore + judge);
        test.setTestAddtime(new Date());
        testMapper.insertSelective(test);

        for (Question t : onesimpleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : onemiddleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : onediffList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : moresimpleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : moremiddleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : morediffList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : judgesimpleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : judgemiddleList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }
        for (Question t : judgediffList){
            TestQuestion tt = new TestQuestion();
            tt.setFkTestId(test.getTestId());
            tt.setFkQuestionId(t.getQuestionId());
            sum += t.getQuestionScore();
            testQuestionMapper.insertSelective(tt);
        }

        test.setTestSimpleSum(onesimple + moresimple + judgesimple);
        test.setTestMiddleSum(onemiddle + moremiddle + judgemiddle);
        test.setTestDiffSum(onediff + morediff + judgediff);

        test.setTestScores(sum);
        Double ceil = Math.ceil(sum * 0.6);
        test.setTestPass(ceil.intValue());

        testMapper.updateByPrimaryKeySelective(test);
    }

    /**
     * List中随机去count个元素
     * @param list
     * @param count
     * @return
     */
    public List<Question> getSubByRadom(List<Question> list, int count){
        List backList = null;
        backList = new ArrayList<Question>();
        Random random = new Random();
        int backSum = 0;
        if (list.size() >= count) {
            backSum = count;
        }else {
            backSum = list.size();
        }
        for (int i = 0; i < backSum; i++) {
//			随机数的范围为0-list.size()-1
            int target = random.nextInt(list.size());
            backList.add(list.get(target));
            list.remove(target);
        }
        return backList;
    }

    /**
     * 通过主键删除
     *
     * @param testId
     */
    public void delTestById(Long testId) {
        testQuestionMapper.DeleteTestUserListByPid(testId);
        testUserQMapper.DeleteTestUserQByPid(testId);
        userQQuestionMapper.DeleteUserQQuestionByPid(testId);
        testMapper.deleteByPrimaryKey(testId);
    }

    /**
     * 更新
     *
     * @param test
     */
//    public void updateTest(Test test) {
//        //数据库只会更新非null字段
//        test.setTestAddtime(null);
//        testMapper.updateByPrimaryKeySelective(test);
//    }

    public void updateTest(Test test) {
        //数据库只会更新非null字段
        test.setTestAddtime(null);
        testMapper.updateByPrimaryKeySelective(test);
    }

    public int canTestUpdate(Long testId) {
//        return 1;
//        System.out.print(testUserQMapper.getTestUserQsByTestId(testId).size());
        return testUserQMapper.getTestUserQsByTestId(testId).size();

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Test findTestById(Long id) {
        Test test = testMapper.selectByPrimaryKey(id);
        ResultBean<Subject> res = subjectClient.findSubjectById(test.getFkTestSubjectId());
        ResultBean<Grade> gradeRes = gradeClient.findGradeById(test.getFkTestGradeId());
        test.setFkSubject(res.getData());
        test.setFkGrade(gradeRes.getData());
        return test;
    }

    /**
     * 查询所有
     *
     * @return
     */
//    public List<Test> findAll() {
//        List<Test> list = testMapper.selectAll();
//        if(!CollectionUtils.isEmpty(list)){
//            for (Test test : list){
//                ResultBean<Subject> res = subjectClient.findSubjectById(test.getFkTestSubjectId());
//                test.setFkSubject(res.getData());
//            }
//        }
//        return list;
//    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param fkTestSubjectId
     * @return
     */
    public PageResult<Test> findTestsByPage(Integer page, Integer rows, Integer fkTestGradeId, Integer fkTestSubjectId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Test.class);

        Example.Criteria criteria = example.createCriteria();

        if(fkTestGradeId != null){
            criteria.andEqualTo("fkTestGradeId", fkTestGradeId);
        }

        if(fkTestSubjectId != null){
            criteria.andEqualTo("fkTestSubjectId", fkTestSubjectId);
        }

        List<Test> list = testMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Test t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTestSubjectId()).getData());
                t.setFkGrade(gradeClient.findGradeById(t.getFkTestGradeId()).getData());

                if(new Date().before(t.getTestBeforetime())){
                    t.setTestState(0); //已考
                }else if((new Date().after(t.getTestBeforetime())) && (new Date().before(t.getTestAftertime()))){
                    t.setTestState(1); //正在考试中
                }else{
                    t.setTestState(2); //考试结束
                }

            }
        }

        PageInfo<Test> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }


    /**
     * 考生查看考试中心时，查出该考试能参加的考试
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param userQId
     * @return
     */
    public PageResult<Test> findTestsByPageByGradeIdAndUserQId(Integer page, Integer rows, Long fkTestGradeId, Long userQId) {
        PageHelper.startPage(page, rows);

        Example example = new Example(Test.class);

        Example.Criteria criteria = example.createCriteria();

        if(fkTestGradeId != null){
            criteria.andEqualTo("fkTestGradeId", fkTestGradeId);
        }


        List<Test> list = testMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Test t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTestSubjectId()).getData());
                t.setFkGrade(gradeClient.findGradeById(t.getFkTestGradeId()).getData());

                if(new Date().before(t.getTestBeforetime())){
                    t.setTestState(0); //已考
                }else if((new Date().after(t.getTestBeforetime())) && (new Date().before(t.getTestAftertime()))){ //正在考试中，看看该用户有没有参加该考试
                    TestUserQ testUserQ = testUserQService.findTestUserQByTestIdAndUserQId(t.getTestId(), userQId);
                    if(testUserQ != null && testUserQ.getTestUserQLasttime() <= 0){
                        t.setTestState(3);  //该用户已参加完该场考试
                    }else{
                        t.setTestState(1);
                    }
                }else{
                    t.setTestState(2); //考试结束
                }

            }
        }

        PageInfo<Test> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
