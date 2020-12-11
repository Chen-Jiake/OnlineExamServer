package com.onlineexam.service;

import com.onlineexam.pojo.Question;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onlineexam.bean.PageResult;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.enums.ExamExceptionEnum;
import com.onlineexam.exception.ExamException;
import com.onlineexam.feign.SubjectClient;
import com.onlineexam.mapper.QuestionMapper;
import com.onlineexam.pojo.Subject;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SubjectClient subjectClient;

    /**
     * 添加
     *
     * @param question
     */
    public void addQuestion(Question question) {
        question.setQuestionAddtime(new Date());
        questionMapper.insertSelective(question);
    }

    /**
     * 通过主键删除
     *
     * @param questionId
     */
    public void delQuestionById(Long questionId) {
        questionMapper.deleteByPrimaryKey(questionId);
    }

    /**
     * 更新
     *
     * @param question
     */
    public void updateQuestion(Question question) {
        //数据库只会更新非null字段
        question.setQuestionAddtime(null);
        questionMapper.updateByPrimaryKeySelective(question);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Question findQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        ResultBean<Subject> res = subjectClient.findSubjectById(question.getFkQuestionSubjectId());
        question.setFkSubject(res.getData());
        return question;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Question> findAll() {
        List<Question> list = questionMapper.selectAll();
        if(!CollectionUtils.isEmpty(list)){
            for (Question question : list){
                ResultBean<Subject> res = subjectClient.findSubjectById(question.getFkQuestionSubjectId());
                question.setFkSubject(res.getData());
            }
        }
        return list;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param questionType
     * @param questionLevel
     * @param fkQuestionSubjectId
     * @return
     */
    public PageResult<Question> findQuestionsByPage(Integer page, Integer rows, Integer questionType, Integer questionLevel, Integer fkQuestionSubjectId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Question.class);

        Example.Criteria criteria = example.createCriteria();

        if(questionType != null){
            criteria.andEqualTo("questionType", questionType);
        }

        if(questionLevel != null){
            criteria.andEqualTo("questionLevel", questionLevel);
        }

        if(fkQuestionSubjectId != null){
            criteria.andEqualTo("fkQuestionSubjectId", fkQuestionSubjectId);
        }

        List<Question> list = questionMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Question t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkQuestionSubjectId()).getData());
            }
        }

        PageInfo<Question> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    /**
     * 上传导入试题
     * @param file
     * @param fkQuestionSubjectId
     */
    public void uploadAndImportExcelQuestion(MultipartFile file, long fkQuestionSubjectId) throws Exception {
        //校验文件类型
        String contentType = file.getContentType();

        //只能是xlsx文件
        if(!contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            throw new ExamException(ExamExceptionEnum.UPLOAD_EXCEL_FILE);
        }

        InputStream inputStream = file.getInputStream();

        //获取工作簿
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);

        //获取工作表
        XSSFSheet sheet = xwb.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        for(int i =2; i <= lastRowNum; i++){
            XSSFRow row = sheet.getRow(i);
            if(row != null){
                //获取每一行
                XSSFCell titleCell = row.getCell(0);
                String title = titleCell.getStringCellValue();

                XSSFCell typeCell = row.getCell(1);
                Double type = typeCell.getNumericCellValue();

                XSSFCell answerCell = row.getCell(6);
                String answer = answerCell.getStringCellValue();

                XSSFCell scoreCell = row.getCell(7);
                Double score = scoreCell.getNumericCellValue();

                XSSFCell levelCell = row.getCell(8);
                Double level = levelCell.getNumericCellValue();

                //构建试题
                Question question = new Question();

                if(type == 0.0 || type == 1.0){ //单选或多选
                    XSSFCell selectACell = row.getCell(2);
                    String selectA = selectACell.getStringCellValue();
                    question.setQuestionSelectA(selectA);

                    XSSFCell selectBCell = row.getCell(3);
                    String selectB = selectBCell.getStringCellValue();
                    question.setQuestionSelectB(selectB);

                    XSSFCell selectCCell = row.getCell(4);
                    String selectC = selectCCell.getStringCellValue();
                    question.setQuestionSelectC(selectC);

                    XSSFCell selectDCell = row.getCell(5);
                    String selectD = selectDCell.getStringCellValue();
                    question.setQuestionSelectD(selectD);
                }else if(type == 2.0){  //判断

                }

                question.setQuestionTitle(title);
                question.setQuestionType(type.intValue());
                question.setQuestionYesanswer(answer);
                question.setQuestionScore(score.intValue());
                question.setQuestionLevel(level.intValue());
                question.setQuestionAddtime(new Date());

                question.setFkQuestionSubjectId(fkQuestionSubjectId);

                questionMapper.insertSelective(question);
            }
        }

        //释放资源
        xwb.close();
    }
}
