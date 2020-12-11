package com.onlineexam.controller;

import com.onlineexam.pojo.Question;
import com.onlineexam.bean.PageResult;
import com.onlineexam.bean.ResultBean;
import com.onlineexam.enums.ExamExceptionEnum;
import com.onlineexam.exception.ExamException;
import com.onlineexam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addQuestion")
    public ResultBean<Void> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param questionId
     * @return
     */
    @DeleteMapping("delQuestionById")
    public ResultBean<Void> delQuestionById(@RequestParam("questionId") Long questionId) {
        try {
            questionService.delQuestionById(questionId);
            return new ResultBean(204, "id为["+questionId+"]的试题删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该试题可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param question
     * @return
     */
    @PutMapping("updateQuestion")
    public ResultBean<Void> updateQuestion(@RequestBody Question question) {
        System.out.println(question);
        questionService.updateQuestion(question);
        return new ResultBean(201, "id为["+question.getQuestionId()+"]的试题修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param questionId
     * @return
     */
    @GetMapping("findQuestionById")
    public ResultBean<Question> findQuestionById(@RequestParam("questionId") Long questionId) {
        Question question = questionService.findQuestionById(questionId);
        if (question == null) {
            return new ResultBean(600, "试题不存在！", null);
        }
        return new ResultBean(200, "查询成功！", question);
    }

    /**
     * 查询所有
     *
     * @return
     */
//    @GetMapping("findQuestions")
//    public ResultBean<List<Question>> findQuestions() {
//        List<Question> list = questionService.findAll();
//        return new ResultBean(200, "查询成功！", list);
//    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param questionType
     * @param questionLevel
     * @param fkQuestionSubjectId
     * @return
     */
    @GetMapping("findQuestionsByPage")
    public ResultBean<PageResult<Question>> findQuestionsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "questionType", required = false) Integer questionType,
            @RequestParam(value = "questionLevel", required = false) Integer questionLevel,
            @RequestParam(value = "fkQuestionSubjectId", required = false) Integer fkQuestionSubjectId) {
        PageResult<Question> questionsByPage = questionService.findQuestionsByPage(page, rows, questionType, questionLevel, fkQuestionSubjectId);
        return new ResultBean(200, "查询成功！", questionsByPage);
    }

    /**
     * 上传导入试题
     *
     * @param file
     * @param fkQuestionSubjectId
     * @return
     */
    @PostMapping("upload")
    public ResultBean<Void> uploadAndImportExcelQuestion(@RequestParam("file") MultipartFile file, @RequestParam("fkQuestionSubjectId") Long fkQuestionSubjectId) {
        if (fkQuestionSubjectId == null) {
            return new ResultBean(600, "请为试题选择所属科目！", null);
        }
        try {
            questionService.uploadAndImportExcelQuestion(file, fkQuestionSubjectId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExamException(ExamExceptionEnum.TOPIX_IMPORT_FIAL);
        }
        return new ResultBean(201, "excel文件试题导入成功！", null);
    }

    @GetMapping("downloadExcel")
    public ResultBean<Void> downloadExcel(HttpServletResponse res) {
        File file = new File("D:\\导入试题模板.xlsx");

        res.setContentType("application/vnd.ms-excel;charset=utf-8");
//        res.setHeader("content-type", "application/octet-stream");
//        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResultBean(200, "试题模板excel文件下载成功！", null);
    }
}
