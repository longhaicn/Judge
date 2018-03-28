package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.po.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface ProjectDao {

    /* 1、 新增一条项目信息
     */
    @Insert({"INSERT INTO t_project (#{project})"})
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "pId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insert(Project project);
    /* 2、 根据项目负责人ID查询项目信息
     */
    @Select("select p_id, p_name, p_description, p_user_id, p_user_name, p_class, p_start, p_end, p_award, datetime from t_project where p_user_id = #{u_id,jdbcType=INTEGER}")
    @Results({
                    @Result(id = true, column = "p_id", property = "pId"),
                    @Result(column = "p_name", property = "pName"),
                    @Result(column = "p_description", property = "pDescription"),
                    @Result(column = "p_user_id", property = "pUserId"),
                    @Result(column = "p_user_name", property = "pUserName"),
                    @Result(column = "p_class", property = "pClass"),
                    @Result(column = "p_start", property = "pStart"),
                    @Result(column = "p_end", property = "pEnd"),
                    @Result(column = "p_award", property = "pAward"),
                    @Result(column = "datetime", property = "datetime")
            })
    List<Project> queryProjectByUId(@Param("u_id") Integer u_id);

    @Select("select * from t_project where p_id = #{p_id}")
    Project queryProjectById(int p_id);

    /* 3、 根据人员ID查询所在项目信息
     */
    @Select("select p.* from t_project p join t_orgnization o on p.p_id = o.o_project_id  where o.o_user_id = #{o_user_id}")
    @Results({
            @Result(id = true, column = "p_id", property = "pId"),
            @Result(column = "p_name", property = "pName"),
            @Result(column = "p_description", property = "pDescription"),
            @Result(column = "p_user_id", property = "pUserId"),
            @Result(column = "p_user_name", property = "pUserName"),
            @Result(column = "p_class", property = "pClass"),
            @Result(column = "p_start", property = "pStart"),
            @Result(column = "p_end", property = "pEnd"),
            @Result(column = "p_award", property = "pAward"),
            @Result(column = "datetime", property = "datetime")
    })
    List<Project> selectProjectByOrguserID(int o_user_id);

    @Select("select * from t_project")
    List<Project> selectAllProject();
}
