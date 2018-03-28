package com.judge.biz;

import com.judge.dao.OrgnizationDao;
import com.judge.dao.UserDao;
import com.judge.po.Orgnization;
import com.judge.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrgnizationBiz")
public class OrgnizationBiz {

    /*
     * 自动注入UserDao
     */
    @Autowired
    private OrgnizationDao orgnizationDao;

    public int insertOrgObj(Orgnization org){
        return orgnizationDao.insertOrgObj(org);
    }

    public List<User> searchUsersByproid(int project_id){
        return orgnizationDao.searchUsersByproid(project_id);
    }

    public List<User> searchUsersByproidAndroleid(int project_id,int roleid){
        return orgnizationDao.searchUsersByproidAndroleid(project_id,roleid);
    }
}
