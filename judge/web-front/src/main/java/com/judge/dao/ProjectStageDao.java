package com.judge.dao;

import com.judge.com.judge.ldriver.SimpleInsertLangDriver;
import com.judge.po.ProjectStage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface ProjectStageDao {
    @Insert("INSERT INTO t_project_stage (#{projectStage})")
    @SelectKey(statement = "SELECT @@IDENTITY", keyProperty = "psId", before = false, resultType = Integer.class, statementType = StatementType.STATEMENT)
    @Lang(SimpleInsertLangDriver.class)
    int insertProjectStageObj(ProjectStage projectStage);

    @Select("select * from t_project_stage where ps_project_id = #{project_id}")
    List<ProjectStage> selectProjectStageByProjectID(int project_id);
}
