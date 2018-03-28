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

    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,o.o_role_id as u_role,u.u_department,u.token,u.datetime from t_user u join t_orgnization o on u.u_id = o.o_user_id where o_project_id =#{project_id}")
    List<User> searchUsersByproid(int project_id);

    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,u.u_role,u.u_department,u.token,u.datetime from t_user u join t_orgnization o on u.u_id = o.o_user_id where o_project_id =#{0} and o_role_id = #{1}")
    List<User> searchUsersByproidAndroleid(int project_id,int roleid);
}
