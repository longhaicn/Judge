package com.judge.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judge.dao.OrgnizationDao;
import com.judge.dao.ProjectDao;
import com.judge.dao.ProjectStageDao;
import com.judge.dao.UserDao;
import com.judge.po.Orgnization;
import com.judge.po.Project;
import com.judge.po.ProjectStage;
import com.judge.po.User;
import com.judge.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("projectBiz")
public class ProjectBiz {
    /*
     * ProjectDao
     */
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectStageDao projectStageDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgnizationDao orgnizationDao;

    public List<Project> queryProjectByUId(int u_id) {
        return projectDao.queryProjectByUId(u_id);
    }

    public Project queryProjectById(int p_id) {
        return projectDao.queryProjectById(p_id);
    }

    public List<Project> selectProjectByOrguserID(int o_user_id) {
        return projectDao.selectProjectByOrguserID(o_user_id);
    }

    public List<Project> selectAllProject() {
        return projectDao.selectAllProject();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertProject(String data) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Project project = new Project();
            JSONObject project_json = JSON.parseObject(data);
            project.setpName(project_json.getString("pName"));
            project.setpDescription(project_json.getString("pDescription"));
            project.setpUserId(project_json.getInteger("pUserId"));
            project.setpUserName(project_json.getString("pUserName"));
            project.setpUserPenalty(project_json.getDouble("pUserPenalty"));
            project.setMajor(project_json.getInteger("major"));
            project.setMajorName(project_json.getString("majorName"));
            project.setpClass(project_json.getInteger("pClass"));
            project.setpStart(sdf.parse(project_json.getString("pStart")));
            project.setpEnd(sdf.parse(project_json.getString("pEnd")));
            project.setpAward(project_json.getLong("pAward"));
            project.setDatetime(new Date());
            projectDao.insert(project);
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
                projectStageDao.insertProjectStageObj(projectStage);
            }

            //add  major
            User major = userDao.findById(project_json.getInteger("major"));
            Orgnization orgnization = new Orgnization();
            orgnization.setoUserId(major.getuId());
            orgnization.setoUserName(major.getuNickname());
            orgnization.setoPenalty(0.00);
            orgnization.setoRoleId(1);//major
            orgnization.setDatetime(new Date());
            orgnization.setoProjectId(project.getpId());
            orgnization.setoProjectName(project.getpName());
            orgnization.setoStatus(0);
            orgnizationDao.insertOrgObj(orgnization);

            //add  projectOwner
            orgnization = new Orgnization();
            orgnization.setoUserId(project.getpUserId());
            orgnization.setoUserName(project.getpUserName());
            orgnization.setoPenalty(project.getpUserPenalty());
            orgnization.setoRoleId(2);
            orgnization.setDatetime(new Date());
            orgnization.setoProjectId(project.getpId());
            orgnization.setoProjectName(project.getpName());
            orgnization.setoStatus(0);
            orgnizationDao.insertOrgObj(orgnization);

            JSONArray users_ja = project_json.getJSONArray("users");
            for (int i = 0; i < users_ja.size(); i++) {
                orgnization = new Orgnization();
                orgnization.setoUserId(users_ja.getJSONObject(i).getIntValue("uId"));
                orgnization.setoUserName(users_ja.getJSONObject(i).getString("uNickname"));
                orgnization.setoRoleId(users_ja.getJSONObject(i).getIntValue("uRole"));
                orgnization.setoPenalty(users_ja.getJSONObject(i).getDoubleValue("uPenalty"));
                orgnization.setDatetime(new Date());
                orgnization.setoProjectId(project.getpId());
                orgnization.setoProjectName(project.getpName());
                orgnization.setoStatus(0);
                orgnizationDao.insertOrgObj(orgnization);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Project> searchProject(String words) {
        return projectDao.searchProject(words);
    }
}
