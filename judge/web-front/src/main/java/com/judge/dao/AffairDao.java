package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.com.judge.ldriver.SimpleSelectInLangDriver;
import com.judge.po.Affair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface AffairDao {
    @Insert("INSERT INTO t_affair (#{affair})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "aId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertAffairObj(Affair affair);

    @Select("select * from t_affair where a_project_id=#{0} and a_start < #{1} and a_end >= #{2}")
    List<Affair> selectAffairBetweenDate(int project_id,String start_date,String end_date);

    @Select("select * from t_affair where a_project_id=#{0} and a_end >= #{1}")
    List<Affair> selectAffairIndate(int project_id,String end_date);

    @Select("select a.* from t_affair a join t_infect i on a.a_id = i.i_affair_id where and a.a_end >= #{0} and i.i_user_id = #{1}")
    List<Affair> selectAffairIndateByUserId(String end_date,int o_user_id);

    @Select("select a.* from t_affair a join t_infect i on a.a_id = i.i_affair_id where i.i_scored = 0 and a.a_end >= #{0} and i.i_user_id = #{1}")
    List<Affair> selectAffairIndateNotdoneByUserId(String end_date,int o_user_id);

    @Select("select * from t_affair where a_project_id = #{0} and a_end < #{1}")
    List<Affair> selectAffairOutdate(int project_id,String end_date);

    @Select("select a.* from t_affair a join t_infect i on a.a_id = i.i_affair_id where a.a_end < #{0} and i.i_user_id = #{1}")
    List<Affair> selectAffairOutdateByUserId(String end_date,int o_user_id);
}
