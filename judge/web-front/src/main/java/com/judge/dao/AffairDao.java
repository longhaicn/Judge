package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.com.judge.ldriver.SimpleSelectInLangDriver;
import com.judge.po.Affair;
import com.judge.po.AffairMiss;
import com.judge.po.Infect;
import org.apache.ibatis.annotations.*;
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

    @Select("select a.*,p.p_name as a_project_name from t_affair a join t_infect i on a.a_id = i.i_affair_id join t_project p on a.a_project_id = p.p_id where i.i_scored = 0 and a.a_end >= #{0} and i.i_user_id = #{1}")
    List<Affair> selectAffairIndateNotdoneByUserId(String end_date,int o_user_id);

    @Select("select * from t_affair where a_project_id = #{0} and a_end < #{1}")
    List<Affair> selectAffairOutdate(int project_id,String end_date);

    @Select("select a.* from t_affair a join t_infect i on a.a_id = i.i_affair_id where a.a_end < #{0} and i.i_user_id = #{1}")
    List<Affair> selectAffairOutdateByUserId(String end_date,int o_user_id);

    @Select("select * from t_affair")
    List<Affair> cpu_selectAll();

    @Select("select count(*) from t_affair where a_project_id = #{0} and datetime BETWEEN CONVERT(datetime,#{1},120) AND CONVERT(datetime,#{2},120)")
    int countAffairNum(int project_id,String start,String end);

    @Select(" select u.u_nickname as username, u.u_id as uId,p.p_name as project,a.a_affairs as affair,a.a_end as endtime  " +
            "from t_project p,t_affair a, t_infect i,t_user u " +
            "where a.a_project_id= p.p_id and a.a_id= i.i_affair_id and i.i_scored='0' and i.i_user_id = u.u_id and i.datetime BETWEEN CONVERT(datetime,#{0},120) AND CONVERT(datetime,#{1},120)")
    @Results({
            @Result(column="project", property="project"),
            @Result(column="username", property="username"),
            @Result(column="uId", property="uId"),
            @Result(column="affair", property="affair"),
            @Result(column="endtime", property="endtime")
    })
    List<AffairMiss> select(String start,String end);

    @Select("select u.u_nickname as username, u.u_id as uId,p.p_name as project,a.a_affairs as affair,a.a_end as endtime " +
            "from t_project p,t_affair a, t_infect i,t_user u " +
            "where a.a_project_id=p.p_id and p.p_id=#{0} and a.a_id= i.i_affair_id and i.i_scored='0' and i.i_user_id = u.u_id and i.datetime BETWEEN CONVERT(datetime,#{1},120) AND CONVERT(datetime,#{2},120)")
    @Results({
            @Result(column="username", property="username"),
            @Result(column="uId", property="uId"),
            @Result(column="project", property="project"),
            @Result(column="affair", property="affair"),
            @Result(column="endtime", property="endtime")
    })
    List<AffairMiss> selectById(int id, String start, String end);
}
