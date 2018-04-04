package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.com.judge.ldriver.SimpleUpdateLangDriver;
import com.judge.po.Affair;
import com.judge.po.Infect;
import com.judge.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface InfectDao {
    @Insert("INSERT INTO t_infect (#{infect})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "iId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertInfectObj(Infect infect);

    @Select("select p.* from t_project p join t_affair a on p.p_id = a.a_project_id join t_infect i on a.a_id = i.i_affair_id where i.i_user_id = #{u_id}")
    List<Infect> selectInfect(int u_id);

    @Select("select * from t_user u join t_infect i on u.u_id = i.i_user_id where i_affair_id = #{affair_id}")
    List<User> selectUsersByAffairId(int affair_id);

    @Update("UPDATE t_infect set i_scored = #{2} WHERE i_affair_id = #{0} and i_user_id = #{1}")
    void updateScore(int affair_id,int u_id,int score);

    @Select("select * from t_infect where i_affair_id = #{a_id}")
    List<Infect> cpu_selectByAId(int a_id);
}
