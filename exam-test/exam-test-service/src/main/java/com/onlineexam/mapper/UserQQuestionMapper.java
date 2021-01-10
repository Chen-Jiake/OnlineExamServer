package com.onlineexam.mapper;

import com.onlineexam.pojo.TestUserQ;
import com.onlineexam.pojo.UserQQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserQQuestionMapper extends Mapper<UserQQuestion> {
    @Delete("DELETE FROM user_q_question WHERE fk_test_id = #{fktestid};")
    void DeleteUserQQuestionByPid(@Param("fktestid") Long testId);

    @Select("select * from user_q_question where fk_question_id = #{fkquestionid}")
    List<UserQQuestion> getUserQQuestionsByQuesId(@Param("fkquestionid") Long questionId);
}
