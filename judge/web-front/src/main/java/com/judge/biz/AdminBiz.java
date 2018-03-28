package com.judge.biz;


import com.judge.dao.AdminDao;
import com.judge.po.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminBiz")
public class AdminBiz {

    @Autowired
    private AdminDao adminDao;

    public Admin adminLogin(String name ,String pwd) {
        return adminDao.adminLogin(name,pwd);
    }

    public int adminchangepwd(String ad_name, String ad_pwd, String new_pwd) {
        return adminDao.adminchangepwd(ad_name,ad_pwd,new_pwd);
    }
}
