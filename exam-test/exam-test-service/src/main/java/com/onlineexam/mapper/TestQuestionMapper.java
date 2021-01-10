package com.onlineexam.mapper;

import com.onlineexam.pojo.TestQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TestQuestionMapper extends Mapper<TestQuestion> {
    @Delete("DELETE FROM test_question WHERE fk_test_id = #{fktestid};")
    void DeleteTestUserListByPid(@Param("fktestid") Long testId);
}
