package com.judge.controller;

import com.alibaba.fastjson.JSONObject;
import com.judge.biz.ProjectBiz;
import com.judge.biz.ProjectStageBiz;
import com.judge.po.Project;
import com.judge.po.ProjectStage;
import com.judge.utils.JsonUtils;
import com.judge.utils.ListObject;
import com.judge.utils.ResponseUtils;
import com.judge.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;


@Controller
public class ProjectController {
    @Autowired
    @Qualifier("projectBiz")
    private ProjectBiz projectBiz;

    @Autowired
    @Qualifier("ProjectStageBiz")
    private ProjectStageBiz projectStageBiz;

    /*
     * 新建项目
     */
    @RequestMapping(value = "/insertProject" , method = RequestMethod.POST)
    public void insertProject(HttpServletRequest request, HttpServletResponse response,String data){
        JSONObject resultObj = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            projectBiz.insertProject(data);
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
    @RequestMapping(value = "/queryProjectByUId")
    public void queryProjectByUId(HttpServletRequest request, HttpServletResponse response,int uId) {
        List<Project> list = projectBiz.queryProjectByUId(uId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("访问成功");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/selectProjectByOrguserID")
    public void selectProjectByOrguserID(HttpServletRequest request, HttpServletResponse response,int uId) {
        List<Project> list = projectBiz.selectProjectByOrguserID(uId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/selectProjectByID")
    public void selectProjectByID(HttpServletRequest request, HttpServletResponse response,int projectId) {
        JSONObject resultObj = new JSONObject();
        Project project = projectBiz.queryProjectById(projectId);
        resultObj.put("code", "0");
        resultObj.put("desc", "success");
        resultObj.put("data",project);
        ResponseUtils.renderJson(response, JsonUtils.toJson(resultObj));
    }

    @RequestMapping(value = "/selectAllProject")
    public void selectAllProject(HttpServletRequest request, HttpServletResponse response) {
        List<Project> list = projectBiz.selectAllProject();
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/selectProjectStageByProjectid")
    public void selectProjectStageByProjectid(HttpServletRequest request, HttpServletResponse response,int projectId) {
        List<ProjectStage> list = projectStageBiz.selectProjectStageByProjectID(projectId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/searchproject")
    public void searchProject(HttpServletRequest request, HttpServletResponse response,String words) {
        try {
            List<Project> list = projectBiz.searchProject(words);
            ListObject listObject = new ListObject();
            listObject.setData(list);
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("success");
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
