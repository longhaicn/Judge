package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.po.Infect;
import com.judge.po.Judge;
import com.judge.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
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
}
