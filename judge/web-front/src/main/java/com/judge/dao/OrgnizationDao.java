package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.com.judge.ldriver.SimpleUpdateLangDriver;
import com.judge.po.Orgnization;
import com.judge.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface OrgnizationDao {
    @Insert("INSERT INTO t_orgnization (#{org})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "oId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertOrgObj(Orgnization org);

    @Update("UPDATE t_orgnization (#{org}) WHERE o_id = #{o_id}")
    @Lang(SimpleUpdateLangDriver.class)
    int updateOrgObj(Orgnization org);

    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,o.o_role_id as u_role,u.u_department,u.token,u.datetime from t_user u join t_orgnization o on u.u_id = o.o_user_id where o_status = 0 and o_project_id =#{project_id}")
    List<User> searchUsersByproid(int project_id);

    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,o.o_role_id as u_role,u.u_department,u.token,u.datetime from t_user u join t_orgnization o on o.o_status = 0 and u.u_id = o.o_user_id where o_project_id =#{0} and o_role_id = #{1}")
    List<User> searchUsersByproidAndroleid(int project_id,int roleid);

    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,o.o_role_id as u_role,u.u_department,u.token,u.datetime from t_user u join t_orgnization o on o.o_status = 0 and u.u_id = o.o_user_id join t_affair a on o.o_project_id = a.a_project_id where a_id =#{0} and o_user_id = #{1}")
    User getUserByAIdAndUId(int a_id,int u_id);

    @Select("select * from t_orgnization where o_project_id =#{0} and o_user_id = #{1}")
    Orgnization getOrgByPIdAndUId(int p_id,int u_id);

    @Select("  select * from t_orgnization o where o.o_status = 0 and o.o_project_id= #{0} and o.o_user_id in  (select j.j_evaluated_id from t_judge j where j.j_project_id= #{0} and datetime  BETWEEN CONVERT(datetime,#{1} ,120) AND CONVERT(datetime,#{2} ,120) group by j.j_evaluated_id having count(j.j_evaluated_id) >0 )")
    List<Orgnization> selectScoreOrgnization(int project_id, String start, String end);

    @Select("select * from t_orgnization o where o.o_status = 0 and o.o_user_id in (select j.j_evaluated_id from t_judge j where datetime  BETWEEN CONVERT(datetime,#{0} ,120) AND CONVERT(datetime,#{1} ,120) group by j.j_evaluated_id having count(j.j_evaluated_id) >0 )")
    List<Orgnization> selectScoreOrgnizations(String start, String end);

    @Select("select * from t_orgnization o where o.o_status = 0 and o.o_project_id=#{0}")
    List<Orgnization> selectOrgnizationByPId(int id);

}
