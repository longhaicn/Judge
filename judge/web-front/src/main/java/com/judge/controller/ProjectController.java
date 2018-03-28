package com.judge.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judge.biz.OrgnizationBiz;
import com.judge.biz.ProjectBiz;
import com.judge.biz.ProjectStageBiz;
import com.judge.po.Orgnization;
import com.judge.po.Project;
import com.judge.po.ProjectStage;
import com.judge.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class ProjectController {
    @Autowired
    @Qualifier("projectBiz")
    private ProjectBiz projectBiz;

    @Autowired
    @Qualifier("ProjectStageBiz")
    private ProjectStageBiz projectStageBiz;

    @Autowired
    @Qualifier("OrgnizationBiz")
    private OrgnizationBiz orgnizationBiz;

    /*
     * 新建项目
     */
    @RequestMapping(value = "/insertProject" , method = RequestMethod.POST)
    public void insertProject(String data, HttpServletRequest request, HttpServletResponse response){
        JSONObject resultObj = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Project project = new Project();
            JSONObject project_json = JSON.parseObject(data);
            project.setpName(project_json.getString("pName"));
            project.setpDescription(project_json.getString("pDescription"));
            project.setpUserId(project_json.getInteger("pUserId"));
            project.setpUserName(project_json.getString("pUserName"));
            project.setpClass(project_json.getInteger("pClass"));
            project.setpStart(new Date());
            project.setpEnd(sdf.parse(project_json.getString("pEnd")));
            project.setpAward(project_json.getLong("pAward"));
            project.setDatetime(project.getpStart());
            projectBiz.insert(project);
            System.out.println(JsonUtils.toJson(project));
            JSONArray stages_ja = project_json.getJSONArray("stages");
            for (int i = 0; i < stages_ja.size(); i++) {
                ProjectStage projectStage = new ProjectStage();
                projectStage.setPsProjectId(project.getpId());
                projectStage.setPsStage(stages_ja.getJSONObject(i).getInteger("psStage"));
                projectStage.setPsStageDescription(stages_ja.getJSONObject(i).getString("psStageDescription"));
                projectStage.setPsStart(sdf.parse(stages_ja.getJSONObject(i).getString("psStart")));
                projectStage.setPsEnd(sdf.parse(stages_ja.getJSONObject(i).getString("psEnd")));
                projectStage.setPsAward(stages_ja.getJSONObject(i).getLong("psAward"));
                projectStage.setDatetime(new Date());
                projectStageBiz.insertProjectStageObj(projectStage);
            }

            JSONArray users_ja = project_json.getJSONArray("users");
            for (int i = 0; i < users_ja.size(); i++) {
                Orgnization orgnization = new Orgnization();
                orgnization.setoUserId(users_ja.getJSONObject(i).getIntValue("u_id"));
                orgnization.setoUserName(users_ja.getJSONObject(i).getString("u_nickname"));
                orgnization.setoRoleId(users_ja.getJSONObject(i).getIntValue("oRoleId"));
                orgnization.setDatetime(new Date());
                orgnization.setoProjectId(project.getpId());
                orgnization.setoProjectName(project.getpName());
                orgnizationBiz.insertOrgObj(orgnization);
            }
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
    public void queryProjectByUId(int uId, HttpServletRequest request, HttpServletResponse response) {
        List<Project> list = projectBiz.queryProjectByUId(uId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("访问成功");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/selectProjectByOrguserID")
    public void selectProjectByOrguserID(int uId, HttpServletRequest request, HttpServletResponse response) {
        List<Project> list = projectBiz.selectProjectByOrguserID(uId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }

    @RequestMapping(value = "/selectProjectByID")
    public void selectProjectByID(int projectId, HttpServletRequest request, HttpServletResponse response) {
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
    public void selectProjectStageByProjectid(int projectId, HttpServletRequest request, HttpServletResponse response) {
        List<ProjectStage> list = projectStageBiz.selectProjectStageByProjectID(projectId);
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(StatusCode.CODE_SUCCESS);
        listObject.setDesc("success");
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }
}
