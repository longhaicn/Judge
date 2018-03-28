package com.judge.dao;

import com.judge.po.Admin;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminDao {

    @Select("select * from t_admin where ad_name = #{0} and ad_password = #{1}")
    Admin adminLogin(String ad_name, String ad_password);

    @Update("update t_admin set ad_password=#{2} where ad_name = #{0} and ad_password = #{1}")
    int adminchangepwd(String ad_name, String ad_pwd, String new_pwd);
}
