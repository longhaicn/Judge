package com.judge.biz;

import com.judge.dao.ProjectDao;
import com.judge.po.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectBiz")
public class ProjectBiz {
    /*
     * ProjectDao
     */
    @Autowired
    private ProjectDao projectDao;
    public int insert(Project project){
        return projectDao.insert(project);
    }
    public List<Project> queryProjectByUId(int u_id){
        return projectDao.queryProjectByUId(u_id);
    }

    public Project queryProjectById(int p_id){
        return projectDao.queryProjectById(p_id);
    }

    public List<Project> selectProjectByOrguserID(int o_user_id){
        return projectDao.selectProjectByOrguserID(o_user_id);
    }
    public List<Project> selectAllProject(){
        return projectDao.selectAllProject();
    }
}
