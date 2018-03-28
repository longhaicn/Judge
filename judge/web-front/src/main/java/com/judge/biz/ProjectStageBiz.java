package com.judge.biz;

import com.judge.dao.ProjectDao;
import com.judge.dao.ProjectStageDao;
import com.judge.po.Project;
import com.judge.po.ProjectStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProjectStageBiz")
public class ProjectStageBiz {
    /*
     * ProjectDao
     */
    @Autowired
    private ProjectStageDao projectStageDao;
    public int insertProjectStageObj(ProjectStage projectStage){
        return projectStageDao.insertProjectStageObj(projectStage);
    }
    public List<ProjectStage> selectProjectStageByProjectID(int project_id){
        return projectStageDao.selectProjectStageByProjectID(project_id);
    }
}
