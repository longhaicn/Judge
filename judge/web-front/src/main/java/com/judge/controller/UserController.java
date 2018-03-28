package com.judge.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judge.biz.UserBiz;
import com.judge.po.User;
import com.judge.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class UserController {    // 自动注入UserService
    private final  String KEY = "poly_2018";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    @Qualifier("userBiz")
    private UserBiz userBiz;

    /*
     * 获取指定id的用户
     */
    @RequestMapping(value = "/findById")
    public void findById(String uId, HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(uId);
        User user = userBiz.findById(id);
        List<User> list = new ArrayList<User>();
        list.add(user);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("访问成功");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/loginByPass")
    public void loginByPass(String uUsername,String uPassword, HttpServletRequest request, HttpServletResponse response){
        User user = userBiz.loginByPass(uUsername,uPassword);
        List<User> list = new ArrayList<User>();
        list.add(user);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("访问成功");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }
    @RequestMapping(value = "/loginByToken")
    public void loginByToken(String uId,String token, HttpServletRequest request, HttpServletResponse response){
        Integer id = Integer.parseInt(uId);
        User user = userBiz.loginByToken(id,token);
        List<User> list = new ArrayList<User>();
        list.add(user);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("访问成功");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @ResponseBody
    @RequestMapping(value = "/searchuser")
    public Object searchuser(String words,HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        List<User> users = userBiz.search(words);
        if(users != null ){
            resultObj.put("code", "0");
            resultObj.put("desc", "success");
            resultObj.put("data",users);
            return resultObj;
        }else{
            resultObj.put("code", "3");
            resultObj.put("desc", "异常,请联系管理员");
            return resultObj;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssologin")
    public Object ssologin(HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        if (request.getParameter("sso")==null || "".equals(request.getParameter("sso"))){
            resultObj.put("code", "1");
            resultObj.put("desc", "sso should not be null");
            return resultObj;
        }
        String ding_id = "" ;
        String oa_id = "";
        String datestr = "" ;
        //单点登陆 ding_id#oa_id#日期
        String b64_ecb_str = request.getParameter("sso");
        if (b64_ecb_str != null && !"".equals(b64_ecb_str)){
            //替换字符
            b64_ecb_str = DesECBUtil.DeReplaceChars(b64_ecb_str);
            try {
                //解密
                b64_ecb_str = DesECBUtil.decrypt(b64_ecb_str, KEY);
                String[] str = b64_ecb_str.split("#");
                ding_id = str[0];
                oa_id = str[1];
                datestr = str[2];
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 5);
                if(sdf.format(calendar.getTime()).compareTo(datestr) == -1){
                    resultObj.put("code", "4");
                    resultObj.put("desc", "非法操作,如有问题联系管理员.");
                    return resultObj;
                }
            } catch (Exception e) {
                resultObj.put("code", "4");
                resultObj.put("desc", "非法操作,如有问题联系管理员.");
                return resultObj;
            }
        }
        User user = userBiz.findByOAId(oa_id);
        if(user != null ){//登陆成功
            request.getSession().setAttribute("current_user", user);
            resultObj.put("code", "0");
            resultObj.put("desc", "success");
            resultObj.put("data",user);
            return resultObj;
        }else{
            resultObj.put("code", "3");
            resultObj.put("desc", "异常,请联系管理员");
            return resultObj;
        }
    }

    public String usersync(HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        String ecb_data = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            ecb_data = DesECBUtil.ReplaceChars(DesECBUtil.encrypt(sdf.format(calendar.getTime()), "poly_2018"));
        } catch (Exception e) {
            e.printStackTrace();
            resultObj.put("code", "3");
            resultObj.put("desc", "异常,请联系管理员");
            return resultObj.toString();
        }
        String result = HttpUtils.doGet("https://oa.shenzhenpoly.com/ehr/pfm_org.jsp?data="+ecb_data);
        JSONObject json = JSON.parseObject(result);
        if("success".equals(json.getString("status"))){
            JSONArray ja = json.getJSONArray("data");
            for (int i = 0; i < ja.size(); i++) {
                JSONObject j = ja.getJSONObject(i);
                User user = new User();
                user.setDingId(j.getString("ding_id"));
                user.setOaId(j.getString("oa_id"));
                user.setuEmail(j.getString("email"));
                user.setuShortName(j.getString("user_short_name"));
                user.setuUsername(j.getString("user_name"));
                user.setuNickname(j.getString("nick_name"));
                user.setuPassword(j.getString("password"));
                user.setuDepartment(j.getString("u_depart_name"));
                user.setuStatus("0");
                userBiz.insertUserObj(user);
            }
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        return resultObj.toString();
    }

    @Scheduled(cron = "0 0 0/6 * * ?")
    public void taskDate(){
        System.out.println("###############################每六小时差异同步人员库###############################");
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        usersyncindate(local_sdf.format(calendar.getTime()));
    }

    @Scheduled(cron = "0 0 0 15 * ?")
    public void taskmonth(){
        System.out.println("###############################每月十五号全量同步人员库###############################");
        usersyncindate(null);
    }

    @ResponseBody
    @RequestMapping(value = "/usersyncindate")
    public String usersyncindate(String datestr){
        JSONObject resultObj = new JSONObject();
        String ecb_data = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            ecb_data = DesECBUtil.ReplaceChars(DesECBUtil.encrypt(sdf.format(calendar.getTime()), "poly_2018"));
        } catch (Exception e) {
            e.printStackTrace();
            resultObj.put("code", "3");
            resultObj.put("desc", "异常,请联系管理员");
            return resultObj.toString();
        }

        StringBuilder sb = new StringBuilder("https://oa.shenzhenpoly.com/ehr/pfm_org.jsp?data="+ecb_data);
        if(!"".equals(datestr) && datestr !=null){
            sb.append("&datestr="+datestr);
        }
        String result = HttpUtils.doGet(sb.toString());

        JSONObject json = JSON.parseObject(result);
        if("success".equals(json.getString("status"))){
            JSONArray ja = json.getJSONArray("data");
            for (int i = 0; i < ja.size(); i++) {
                JSONObject j = ja.getJSONObject(i);
                User user = new User();
                user.setDingId(j.getString("ding_id"));
                user.setOaId(j.getString("oa_id"));
                user.setuEmail(j.getString("email"));
                user.setuShortName(j.getString("user_short_name"));
                user.setuUsername(j.getString("user_name"));
                user.setuNickname(j.getString("nick_name"));
                user.setuPassword(j.getString("password"));
                user.setuDepartment(j.getString("u_depart_name"));
                user.setuStatus("0");
                userBiz.updateUserObj(user);
            }
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        return resultObj.toString();
    }
}
