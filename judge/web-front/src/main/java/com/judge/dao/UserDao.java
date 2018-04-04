package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.com.judge.ldriver.SimpleSelectInLangDriver;
import com.judge.com.judge.ldriver.SimpleUpdateLangDriver;
import com.judge.po.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface UserDao {
    /* 1、 根据用户ID查询用户信息
     * 查询某个公告的信息
     */
    @Select("select * from t_user where u_id = #{u_id}")
    User findById(Integer u_id);

    @Select("select * from t_user where oa_id = #{oa_id}")
    User findByOAId(String oa_id);

    @Select("select * from t_user where ding_id = #{ding_id}")
    User findByDingId(String ding_id);
    /* 2、 根据用户ID查询用户信息
     * 查询某个公告的信息
     */
    @Select("select * from t_user where u_username = #{0} and u_password = #{1}")
    User loginByPass(String u_username, String u_password);
    /* 3、 根据用户ID查询用户信息
     * 查询某个公告的信息
     */
    @Select("select * from t_user where u_id = #{0} and token = #{1}")
    User loginByToken(Integer u_id, String token);

    /* 4、 根据用户ID查询用户信息
     * 查询某个公告的信息
     */
    @Insert("INSERT INTO t_user(oa_id, ding_id, u_nickname, u_short_name, u_username, u_password, u_email, u_status, u_role, u_department, token, datetime) VALUES ( #{0},  #{1},  #{2},  #{3},  #{4},  #{5},  #{6},  #{7},  #{8},  #{9},  #{10},  #{11});")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "uId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    int insertUser(Integer oa_id,Integer ding_id,String u_nickname,String u_short_name,String u_username,String u_password,String u_email,Integer u_status,Integer u_role,String u_department,String token,String datetime);

    @Insert("INSERT INTO t_user (#{user})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "uId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertUserObj(User user);

    @Update("UPDATE t_user (#{user}) WHERE oa_id = #{oaId}")
    @Lang(SimpleUpdateLangDriver.class)
    int updateUserObj(User user);

    @Select("select u_id,oa_id,ding_id,u_nickname,u_short_name,u_username,u_email,u_status,u_role,u_department,token,datetime from t_user where u_short_name like #{words} or u_nickname  like #{words} or u_username  like #{words}")
    List<User> search(String words);

    @Select("select u_id,oa_id,ding_id,u_nickname,u_short_name,u_username,u_email,u_status,u_role,u_department,token,datetime from t_user where u_id in (#{userids})")
    @Lang(SimpleSelectInLangDriver.class)
    List<User> selectInIds(@Param("userids")int[] userids);

    /**
     * 非CEO角色调用的 需要评分人员
     * @param a_id
     * @param u_id
     * @return
     */
    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,u.u_role,u.u_department,u.token,u.datetime \n" +
            "  from t_infect i join t_user u on i.i_user_id = u.u_id \n" +
            "  join t_affair a on i.i_affair_id = a.a_id \n" +
            "  join t_orgnization o on a.a_project_id = o.o_project_id and i.i_user_id = o.o_user_id \n" +
            "  where o.o_role_id !=1 and i.i_affair_id = #{0} and u.u_id != #{1}")
    List<User> selectUsersForJudge(int a_id,int u_id);

    /**
     * CEO角色调用的 需要评分人员
     * @param a_id
     * @return
     */
    @Select("select u.u_id,u.oa_id,u.ding_id,u.u_nickname,u.u_short_name,u.u_username,u.u_email,u.u_status,u.u_role,u.u_department,u.token,u.datetime \n" +
            "  from t_affair a join t_user u on a.a_sponser_id = u.u_id \n" +
            "  where a.a_id = #{a_id}")
    List<User> selectUsersForJudgeCEO(int a_id);
}
