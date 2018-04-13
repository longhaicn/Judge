package com.judge.biz;

import com.judge.dao.*;
import com.judge.po.*;
import com.judge.utils.DateTimeUtil;
import com.judge.utils.FileUtil;
import com.judge.utils.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("scoreReportBiz")
public class ScoreReportBiz {

    @Autowired
    private JudgeDao judgeDao;
    @Autowired
    private OrgnizationDao orgnizationDao;
    @Autowired
    private InfectDao infectDao;
    @Autowired
    private AffairDao affairDao;
    @Autowired
    private ProjectStageDao projectStageDao;

    public List<ScoreReport> getScoreReprot(int id, String sr_start, String sr_end) {
        //查询项目ID中没有评分的人
        List<AffairMiss> affairMissList = affairDao.selectById(id, sr_start, sr_end + " 23:59:59");
        //查询被评分的项目成员（出去级别1的人员）
        List<Orgnization> orgnizations = orgnizationDao.selectScoreOrgnization(id, sr_start, sr_end + " 23:59:59");//project_id, DateTimeUtil.parseDateStr(sr_start), DateTimeUtil.parseDateStr(sr_end));
        //查询所有项目成员
        List<Orgnization> orgnizationList = orgnizationDao.selectOrgnizationByPId(id);
        //解决t_judge表中j_evaluator_role_id为空的问题，暂时处理，希望后面能改善这个问题
        for (Orgnization orgnization : orgnizationList) {
            judgeDao.updateRoles(orgnization.getoProjectId(), orgnization.getoUserId(), orgnization.getoRoleId());
            //System.out.println(orgnization.toString());
        }
        //获取项目ID为sr_project_id在时间段[sr_start,sr_end]内的所有评分记录
        List<Judge> listJudge = listJudge = judgeDao.selectScoreReport(id, sr_start, sr_end + " 23:59:59");

        //List<ScoreReport> listScore 作为返回值
        List<ScoreReport> listScore = new ArrayList<ScoreReport>();
        //总计当前人员在项目中的得分情况
        ScoreReport userScore;
        //小计每一条judge记录的三维总分
        int sum;
        //roles统计给当前人员打分的人所属角色的数量
        int roles[];
        int Atitude[];
        int QualityEfficient[];
        int Complishment[];
        Resource resource = new ClassPathResource("/");
        String path = null;
        try {
            path = resource.getFile().getParentFile().getParentFile().getPath() + File.separator + "temp";
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("######0.00");

        //绩效考核报表名称
        String sheetName = "绩效考核报表" + DateTimeUtil.getDateTimeStr();


        //判断集合非空
        if (!orgnizations.isEmpty() && !listJudge.isEmpty()) {
            //计算每个角色的总人数 roles[]
            for (Orgnization orgnization : orgnizations) {
                userScore = new ScoreReport();
                userScore.setRole1_score(0);
                userScore.setRole2_score(0);
                userScore.setRole3_score(0);
                userScore.setRole4_score(0);

                if (1 == orgnization.getoRoleId()) {
                    userScore.setuRole("最高项目负责人");
                } else if (2 == orgnization.getoRoleId()) {
                    userScore.setuRole("项目总负责人");
                } else if (3 == orgnization.getoRoleId()) {
                    userScore.setuRole("项目执行负责人");
                } else if (4 == orgnization.getoRoleId()) {
                    userScore.setuRole("组员");
                }
                userScore.setpName(orgnization.getoProjectName());
                userScore.setpStart(sr_start);
                userScore.setpEnd(sr_end);
                roles = new int[4];
                String affairs = "";

                Atitude = new int[3];
                QualityEfficient = new int[3];
                Complishment = new int[3];
                for (Judge judge : listJudge) {
                    //小计每一条judge记录的三维总分
                    sum = 0;
                    if (orgnization.getoUserId().equals(judge.getjEvaluatedId())) {
                        if (!affairs.contains("[" + judge.getjAffairId() + "]")) {
                            affairs += "[" + judge.getjAffairId() + "]-";
                        }
//                        System.out.println(judge.toString());
                        Atitude[judge.getjAtitude()]++;
                        QualityEfficient[judge.getjQualityEfficient()]++;
                        Complishment[judge.getjComplishment()]++;

                        //roles统计给当前人员打分的人所属角色的数量
                        roles[judge.getjEvaluatorRoleId() - 1]++;
                        userScore.setuId(orgnization.getoUserId());
                        userScore.setuUsername(orgnization.getoUserName());
                        //判断态度评分是否为零，为零则小计分数为零，并且过滤掉不清楚的数据。
                        if (0 == judge.getjAtitude() || null == judge.getjAtitude() || null == judge.getjQualityEfficient() || null == judge.getjComplishment()) {
                            sum = 0;
                        } else {
                            sum = judge.getjAtitude() + judge.getjQualityEfficient() + judge.getjComplishment();
                        }
                        if (1 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole1_score(userScore.getRole1_score() + sum);
                        } else if (2 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole2_score(userScore.getRole2_score() + sum);
                        } else if (3 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole3_score(userScore.getRole3_score() + sum);
                        } else if (4 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole4_score(userScore.getRole4_score() + sum);
                        }
                    }
                }
//                System.out.println("affairs:" + affairs);
                userScore.setuAfairSum(affairs.split("-").length);
                //计算从不同角色那里得分的总分数
                double getRole1_score = 0;
                double getRole2_score = 0;
                double getRole3_score = 0;
                double getRole4_score = 0;

                if (0 < roles[0]) {
                    getRole1_score = (double) userScore.getRole1_score() * 50 / (6 * roles[0]);
                }
                if (0 < roles[1]) {
                    getRole2_score = (double) userScore.getRole2_score() * 50 / (6 * roles[1]);
                }
                if (0 < roles[2]) {
                    getRole3_score = (double) userScore.getRole3_score() * 30 / (6 * roles[2]);
                }
                if (0 < roles[3]) {
                    getRole4_score = (double) userScore.getRole4_score() * 20 / (6 * roles[3]);
                }
                //计算人员最终得分
                userScore.setScoreSum(getRole1_score + getRole2_score + getRole3_score + getRole4_score);
                for (AffairMiss affairMiss : affairMissList) {
                    if (affairMiss.getuId() == userScore.getuId()) {
                        userScore.setScoreSum(0.0);
                    }
                }
                userScore.setEvSum(df.format(userScore.getScoreSum()));
                userScore.setFileName(sheetName);
                userScore.setAtitudeDetail("★:" + Atitude[0] + " ★★:" + Atitude[1] + " ★★★:" + Atitude[2]);
                userScore.setQualityEfficientDetail("★:" + QualityEfficient[0] + " ★★:" + QualityEfficient[1] + " ★★★:" + QualityEfficient[2]);
                userScore.setComplishmentDetail("★:" + Complishment[0] + " ★★:" + Complishment[1] + " ★★★:" + Complishment[2]);

                listScore.add(userScore);
            }
        }
        //计算奖金
        long stageBonus = 1;
        ProjectStage projectStage = projectStageDao.selectProjectStageByPsId(id, sr_start, sr_end);
        if (projectStage == null || projectStage.getPsAward() == 0) {
            stageBonus = 1;
        } else {
            stageBonus = projectStage.getPsAward();
        }
        //拿当前时间去结算，获取奖金额度（1.到了月底则结算，2.没到月底，不结算）
        int ASUM = 0;

        if (!listScore.isEmpty()) {
            for (ScoreReport score : listScore) {
                if (score.getScoreSum() > 80.00 && !"项目总负责人".equals(score.getuRole())) {// && ! "项目总负责人".equals(score.getuRole())
                    ASUM += score.getuAfairSum();
                }
                if (score.getScoreSum() < 50.00) {
                    score.setuPerformance("扣除");
                } else {
                    score.setuPerformance("全奖");
                }
            }
            for (ScoreReport score : listScore) {
                if (score.getScoreSum() > 80.00 && !"项目总负责人".equals(score.getuRole())) {//&& ! "项目总负责人".equals(score.getuRole())
                    score.setuProportion(df.format(stageBonus * (double) score.getuAfairSum() / ASUM));
                } else {
                    score.setuProportion("0");
                }
            }
        }

        try {
            Collections.sort(listScore, new Comparator<ScoreReport>() {
                /*
                 * int compare(ScoreReport o1, ScoreReport o2) 返回一个基本类型的整型，
                 * 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。
                 */
                public int compare(ScoreReport o1, ScoreReport o2) {
                    //按照分数大小进行降序排列
                    if (o1.getScoreSum() < o2.getScoreSum()) {
                        return 1;
                    }
                    if (o1.getScoreSum() == o2.getScoreSum()) {
                        return 0;
                    }
                    return -1;
                }
            });
            FileUtil.deleteDir(path);
            POIUtil.writeExcel03(path, sheetName, listScore);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listScore;
    }

    public List<ScoreReport> getScoreReprots(String sr_start, String sr_end) {
        //查询时间段内被评分的组织人员
        List<Orgnization> orgnizations = orgnizationDao.selectScoreOrgnizations(sr_start, sr_end);//project_id, DateTimeUtil.parseDateStr(sr_start), DateTimeUtil.parseDateStr(sr_end));

        //查询未评分的人
        List<AffairMiss> affairMissList = affairDao.select(sr_start, sr_end);

        //解决t_judge表中j_evaluator_role_id为空的问题，暂时处理，希望后面能改善这个问题
        for (Orgnization orgnization : orgnizations) {
            judgeDao.updateRoles(orgnization.getoProjectId(), orgnization.getoUserId(), orgnization.getoRoleId());

            System.out.println(orgnization.toString());
        }
        //获取项目ID为sr_project_id在时间段[sr_start,sr_end]内的所有评分记录
        List<Judge> listJudge = judgeDao.selectScoreReports(sr_start, sr_end);

        List<ScoreReport> listScore = new ArrayList<ScoreReport>();
        //总计当前人员在项目中的得分情况
        ScoreReport userScore;
        //小计每一条judge记录的三维总分
        int sum;
        //roles统计给当前人员打分的人所属角色的数量
        int roles[];
        //统计每个人收到态度，质量效率，目标完成性的各个等级统计数
        int Atitude[];
        int QualityEfficient[];
        int Complishment[];

        Resource resource = new ClassPathResource("/");
        String path = null;
        try {
            path = resource.getFile().getParentFile().getParentFile().getPath() + File.separator + "temp";
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("######0.00");

        //绩效考核报表名称
        String sheetName = "绩效考核报表" + DateTimeUtil.getDateTimeStr();
        //
        String uids = "";
        //判断集合非空
        if (!orgnizations.isEmpty() && !listJudge.isEmpty()) {
            //计算每个角色的总人数 roles[]
            for (Orgnization orgnization : orgnizations) {
                userScore = new ScoreReport();
                userScore.setRole1_score(0);
                userScore.setRole2_score(0);
                userScore.setRole3_score(0);
                userScore.setRole4_score(0);
                if (uids.contains("[" + orgnization.getoUserId() + "]")) {
                    continue;
                } else {
                    uids += "[" + orgnization.getoUserId() + "]";
                }
                userScore.setpStart(sr_start);
                userScore.setpEnd(sr_end);
                roles = new int[4];
                String affairs = "";

                Atitude = new int[3];
                QualityEfficient = new int[3];
                Complishment = new int[3];

                for (Judge judge : listJudge) {
                    //小计每一条judge记录的三维总分
                    if (orgnization.getoUserId().equals(judge.getjEvaluatedId())) {
                        System.out.println("judge.getjAtitude()：" + judge.getjAtitude());
                        ++Atitude[judge.getjAtitude()];
                        QualityEfficient[judge.getjQualityEfficient()]++;
                        Complishment[judge.getjComplishment()]++;

                        if (!affairs.contains("[" + judge.getjAffairId() + "]")) {
                            affairs += "[" + judge.getjAffairId() + "]-";
                        }
                        System.out.println(judge.toString());
                        //roles统计给当前人员打分的人所属角色的数量
                        roles[judge.getjEvaluatorRoleId() - 1]++;
                        userScore.setuId(orgnization.getoUserId());
                        userScore.setuUsername(orgnization.getoUserName());
                        //判断态度评分是否为零，为零则小计分数为零，并且过滤掉不清楚的数据。
                        if (0 == judge.getjAtitude() || null == judge.getjAtitude() || null == judge.getjQualityEfficient() || null == judge.getjComplishment()) {
                            sum = 0;
                        } else {
                            sum = judge.getjAtitude() + judge.getjQualityEfficient() + judge.getjComplishment();
                        }
                        if (1 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole1_score(userScore.getRole1_score() + sum);
                        } else if (2 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole2_score(userScore.getRole2_score() + sum);
                        } else if (3 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole3_score(userScore.getRole3_score() + sum);
                        } else if (4 == judge.getjEvaluatorRoleId()) {
                            userScore.setRole4_score(userScore.getRole4_score() + sum);
                        }
                    }
                }
                System.out.println("affairs:" + affairs);
                userScore.setuAfairSum(affairs.split("-").length);
                //计算从不同角色那里得分的总分数
                double getRole1_score = 0;
                double getRole2_score = 0;
                double getRole3_score = 0;
                double getRole4_score = 0;

                if (0 < roles[0]) {
                    getRole1_score = (double) userScore.getRole1_score() * 50 / (6 * roles[0]);
                }
                if (0 < roles[1]) {
                    getRole2_score = (double) userScore.getRole2_score() * 50 / (6 * roles[1]);
                }
                if (0 < roles[2]) {
                    getRole3_score = (double) userScore.getRole3_score() * 30 / (6 * roles[2]);
                }
                if (0 < roles[3]) {
                    getRole4_score = (double) userScore.getRole4_score() * 20 / (6 * roles[3]);
                }
                //计算人员最终得分
                userScore.setScoreSum(getRole1_score + getRole2_score + getRole3_score + getRole4_score);
                //未评分的人自己为0分
                for (AffairMiss affairMiss : affairMissList) {
                    if (affairMiss.getuId() == userScore.getuId()) {
                        userScore.setScoreSum(0.0);
                    }
                }
                userScore.setEvSum(df.format(userScore.getScoreSum()));
                userScore.setFileName(sheetName);
                userScore.setAtitudeDetail("★:" + Atitude[0] + " ★★:" + Atitude[1] + " ★★★:" + Atitude[2]);
                userScore.setQualityEfficientDetail("★:" + QualityEfficient[0] + " ★★:" + QualityEfficient[1] + " ★★★:" + QualityEfficient[2]);
                userScore.setComplishmentDetail("★:" + Complishment[0] + " ★★:" + Complishment[1] + " ★★★:" + Complishment[2]);
//                System.out.println(userScore.toString());
//                System.out.println(roles[0]);
//                System.out.println(roles[1]);
//                System.out.println(roles[2]);
//                System.out.println(roles[3]);
                listScore.add(userScore);

            }
        }

        //计算奖金
        //拿当前时间去结算，获取奖金额度（1.到了月底则结算，2.没到月底，不结算）
        int ASUM = 0;

        if (!listScore.isEmpty()) {
            for (ScoreReport score : listScore) {
                if (score.getScoreSum() > 80.00) {
                    ASUM += score.getuAfairSum();
                }
                if (score.getScoreSum() < 50.00) {
                    score.setuPerformance("扣减");
                } else {
                    score.setuPerformance("-");
                }
            }
            for (ScoreReport score : listScore) {
                if (score.getScoreSum() > 80.00) {
                    score.setuProportion("" + (double) score.getuAfairSum() / ASUM);
                } else {
                    score.setuProportion("0");
                }
            }
            System.out.println("ASUM:" + ASUM);
        }

        try {
            FileUtil.deleteDir(path);
            POIUtil.writeExcel03s(path, sheetName, listScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listScore;
    }

    public ScoreReport getScoreReprotById(int id, int uid, String sr_start, String sr_end) {
        System.out.println("sr_start+sr_end" + sr_start + sr_end);
        List<ScoreReport> list = getScoreReprot(id, sr_start, sr_end);

        ScoreReport scoreReport = null;

        for (ScoreReport srpt : list) {
            if (srpt.getuId() == uid) {
                scoreReport = srpt;
            }
        }
        return scoreReport;
    }
}
