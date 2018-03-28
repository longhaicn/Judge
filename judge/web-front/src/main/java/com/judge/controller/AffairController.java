package com.judge.controller;

import com.alibaba.fastjson.JSONObject;
import com.judge.biz.AffairBiz;
import com.judge.po.Affair;
import com.judge.utils.JsonUtils;
import com.judge.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AffairController {    // 自动注入UserService
    @Autowired
    private AffairBiz affairBiz;

    @RequestMapping(value = "/newAffair" , method = RequestMethod.POST)
    public void newAffair(int aProjectId, int aSponserId, String aAffairs, String aEnd, String uIds, HttpServletRequest request, HttpServletResponse response){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject resultObj = new JSONObject();
        String[] str_userids = uIds.split("-");
        int[] int_userids = new int[str_userids.length];
        for (int i = 0; i < str_userids.length; i++) {
            int_userids[i] = Integer.valueOf(str_userids[i]);
        }
        try{
            Affair affair = new Affair();
            affair.setaAffairs(aAffairs);
            affair.setaProjectId(aProjectId);
            affair.setaSponserId(aSponserId);
            affair.setaStart(new Date());
            affair.setDatetime(affair.getaStart());
            affair.setaEnd(sdf.parse(aEnd));
            affairBiz.newAffair(affair,int_userids);
        }catch (Exception e){
            resultObj.put("code", "1");
            resultObj.put("desc", "error:"+e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
            return;
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
    }
    @RequestMapping(value = "/selectAffairIndate")
    public void selectAffairIndate(int uId,HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        try{
            resultObj.put("data",affairBiz.selectAffairIndateByUserId(uId));
        }catch (Exception e){
            resultObj.put("code", "1");
            resultObj.put("desc", "error:"+e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
            return;
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
        return;
    }

    @RequestMapping(value = "/selectAffairIndateNotdoneByUserId")
    public void selectAffairIndateNotdoneByUserId(int uId,HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        try{
            resultObj.put("data",affairBiz.selectAffairIndateNotdoneByUserId(uId));
        }catch (Exception e){
            resultObj.put("code", "1");
            resultObj.put("desc", "error:"+e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
            return;
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
        return;
    }

    @RequestMapping(value = "/selectUsersForJudge")
    public void selectUsersForJudge(int aId,int uId,HttpServletRequest request,HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        try{
            resultObj.put("data",affairBiz.selectUsersForJudge(aId,uId));
        }catch (Exception e){
            resultObj.put("code", "1");
            resultObj.put("desc", "error:"+e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
            return;
        }
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
        return;
    }
}
