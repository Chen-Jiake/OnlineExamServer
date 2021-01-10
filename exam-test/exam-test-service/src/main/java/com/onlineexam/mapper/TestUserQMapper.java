package com.onlineexam.mapper;

import com.onlineexam.pojo.RoleRights;
import com.onlineexam.pojo.TestUserQ;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TestUserQMapper extends Mapper<TestUserQ> {

    @Delete("DELETE FROM test_user_q WHERE fk_test_id = #{fktestid};")
    void DeleteTestUserQByPid(@Param("fktestid") Long testId);

    @Select("select * from test_user_q where fk_test_id = #{fktestid}")
    List<TestUserQ> getTestUserQsByTestId(@Param("fktestid") Long testId);
}
