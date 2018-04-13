package com.judge.biz;

import com.judge.dao.UserDao;
import com.judge.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userBiz")
public class UserBiz {

    /*
     * 自动注入UserDao
     */
    @Autowired
    private UserDao userDao;

    public User findById(Integer info_id) {
        return userDao.findById(info_id);
    }

    public User findByOAId(String oa_id) {
        return userDao.findByOAId(oa_id);
    }

    public User findByDingId(String ding_id) {
        return userDao.findByDingId(ding_id);
    }

    public User loginByPass(String u_username, String u_password) {
        return userDao.loginByPass(u_username, u_password);
    }

    public User loginByToken(Integer u_id, String token) {
        return userDao.loginByToken(u_id, token);
    }

    public int insertUserObj(User user) {
        return userDao.insertUserObj(user);
    }

    public int updateUserObj(User user) {
        if (userDao.findByOAId(user.getOaId()) != null) {
            return userDao.updateUserObj(user);
        } else {
            return userDao.insertUserObj(user);
        }
    }

    public List<User> search(String words) {
        words = "%" + words + "%";
        return userDao.search(words);
    }
}
