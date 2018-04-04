package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.po.Infect;
import com.judge.po.Judge;
import com.judge.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface JudgeDao {
    @Insert("INSERT INTO t_judge (#{judge})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "jId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertJudgeObj(Judge judge);

    @Select("select p.* from t_project p join t_affair a on p.p_id = a.a_project_id join t_infect i on a.a_id = i.i_affair_id where i.i_user_id = #{u_id}")
    List<Infect> selectInfect(int u_id);

    @Select("select * from t_user u join t_infect i on u.u_id = i.i_user_id where i_affair_id = #{affair_id}")
    List<User> selectUsersByAffairId(int affair_id);

    @Select("select j_affair_id,j_project_id,j_evaluator_id,o_role_id as j_evaluator_role_id,j_evaluated_id,j_atitude,j_quality_efficient,j_complishment,j_reason,o.o_role_id from t_judge j join t_orgnization o on j.j_evaluator_id = o.o_user_id  and o.o_project_id = j.j_project_id where j.j_affair_id = #{affair_id}")
    List<Judge> cpu_selectByAId(int affair_id);

    @Select("select * from t_judge j where j.j_project_id= #{0} and datetime BETWEEN CONVERT(datetime,#{1},120) AND CONVERT(datetime,#{2},120)")
    List<Judge> selectScoreReport(int sr_project_id, String sr_start, String sr_end);

    @Select("select * from t_judge j where datetime BETWEEN CONVERT(datetime,#{0},120) AND CONVERT(datetime,#{1},120)")
    List<Judge> selectScoreReports(String sr_start, String sr_end);

    @Update("UPDATE t_judge SET j_evaluator_role_id = #{2} where j_project_id=#{0} and j_evaluator_id=#{1}")
    void updateRoles(Integer sr_project_id, Integer integer, Integer integer1);

}
