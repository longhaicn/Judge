package com.judge.controller;


import com.judge.biz.AdminBiz;
import com.judge.po.Admin;
import com.judge.utils.JsonUtils;
import com.judge.utils.ListObject;
import com.judge.utils.ResponseUtils;
import com.judge.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {


    @Autowired
    @Qualifier("adminBiz")
    private AdminBiz adminBiz;
    /*
     * 获取指定id的用户
     */
        @RequestMapping(value = "/adminLogin")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,String adName,String adPassword) {

        Admin admin = adminBiz.adminLogin(adName,adPassword);
        List<Admin> list = new ArrayList<Admin>();
        list.add(admin);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        if(null==admin){
            listObject.setCode(StatusCode.CODE_ERROR);
            listObject.setDesc("密码错误");
        }else{
            request.getSession().setAttribute("current_user", admin);
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("登录成功");
        }

        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    /*
     * 获取指定id的用户
     */
    @RequestMapping(value = "/adminchangepwd")
    public void adminchangepwd(HttpServletRequest request, HttpServletResponse response,String adName,String adPassword,String newPwd) {

        int admin = adminBiz.adminchangepwd(adName,adPassword,newPwd);
        List<String> list = new ArrayList<String>();
        list.add("修改密码");
        ListObject listObject = new ListObject();
        listObject.setData(list);
        if(admin < 1){
            listObject.setCode(StatusCode.CODE_ERROR);
            listObject.setDesc("修改失败");
        }else{
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("修改成功");
        }
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }


}
