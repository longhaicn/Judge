package com.judge.biz;

import com.judge.dao.AffairDao;
import com.judge.dao.InfectDao;
import com.judge.dao.JudgeDao;
import com.judge.po.Affair;
import com.judge.po.FeedbackRecord;
import com.judge.po.Infect;
import com.judge.po.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("JudgeBiz")
public class JudgeBiz {
    @Autowired
    private JudgeDao judgeDao;

    @Autowired
    private AffairDao affairDao;

    @Autowired
    private InfectDao infectDao;

    /**
     * percent of role
     */
    private static Map<Integer, Double> percent_ap = new HashMap<Integer, Double>();

    static {
        percent_ap.put(1, 0.5);
        percent_ap.put(2, 0.5);
        percent_ap.put(3, 0.3);
        percent_ap.put(4, 0.2);
    }

    /**
     * 发起评分事件 and 更新回执表
     */
    @Transactional(rollbackFor = Exception.class)
    public void newJudgeList(List<Judge> judgeList) {
        for (int i = 0; i < judgeList.size(); i++) {
            judgeDao.insertJudgeObj(judgeList.get(i));
        }
        infectDao.updateScore(judgeList.get(0).getjAffairId(), judgeList.get(0).getjEvaluatorId(), 1);
    }


    public void compute() {
        List<Affair> affairList = affairDao.cpu_selectAll();
        Map<String, Double> uid_score_map = new HashMap<String, Double>();
        Map<String, Integer> uid_time_map = new HashMap<String, Integer>();

        Map<String, Double> p_uid_score_map = new HashMap<String, Double>();
        Map<String, Integer> p_uid_time_map = new HashMap<String, Integer>();

        for (int ai = 0; ai < affairList.size(); ai++) {
            int[] a_data = new int[6];
            int aId = affairList.get(ai).getaId();
            int pId = affairList.get(ai).getaProjectId();
            List<Infect> infectList = infectDao.cpu_selectByAId(aId);
            List<Integer> uids_need_to_pf = new ArrayList<Integer>();
            List<Integer> uids_did_not_pf = new ArrayList<Integer>();
            for (int j = 0; j < infectList.size(); j++) {
                uids_need_to_pf.add(infectList.get(j).getiUserId());
                if (infectList.get(j).getiScored() == 0) {
                    uids_did_not_pf.add(infectList.get(j).getiUserId());
                }
            }
            //都没有评分
            if (uids_need_to_pf.size() == uids_did_not_pf.size()) System.out.println("都没有评分");
            List<Judge> judgeList = judgeDao.cpu_selectByAId(aId);
            String p_a_u_str = "";
            int other_score = 0;
            int atitude_score = 0;
            double total_score = 0;
            for (int j = 0; j < judgeList.size(); j++) {
                p_a_u_str = pId + "-" + aId + "-" + judgeList.get(j).getjEvaluatedId();
                other_score = (judgeList.get(j).getjQualityEfficient() == null ? 0 : judgeList.get(j).getjQualityEfficient())
                        + (judgeList.get(j).getjComplishment() == null ? 0 : judgeList.get(j).getjComplishment());
                atitude_score = judgeList.get(j).getjAtitude() == null ? 0 : judgeList.get(j).getjAtitude();
                //态度分为0分，全为0分
                total_score = atitude_score == 0 ? 0 : (other_score + atitude_score);
                total_score = percent_ap.get(judgeList.get(j).getjEvaluatorRoleId()) * total_score;
                uid_score_map.put(p_a_u_str, uid_score_map.get(p_a_u_str) == null ? total_score : total_score + uid_score_map.get(p_a_u_str));
                uid_time_map.put(p_a_u_str, uid_time_map.get(p_a_u_str) == null ? 1 : uid_time_map.get(p_a_u_str) + 1);
            }
        }
        String p_u_str = "";
//        for (String key : uid_score_map.keySet()) {
//            System.out.println(key + "#" + uid_score_map.get(key)+ "#" + uid_score_map.get(key)/uid_time_map.get(key) + "#" + uid_time_map.get(key));
//            String[] key_strs = key.split("-");
//            p_u_str = key_strs[0] +"-" +  key_strs[2];
//            other_score = (judgeList.get(j).getjQualityEfficient() ==null?0:judgeList.get(j).getjQualityEfficient())
//                    + (judgeList.get(j).getjComplishment() ==null?0:judgeList.get(j).getjComplishment());
//            atitude_score = judgeList.get(j).getjAtitude() == null ? 0 : judgeList.get(j).getjAtitude();
//            //态度分为0分，全为0分
//            total_score = atitude_score == 0 ? 0:(other_score+atitude_score);
//            total_score = percent_ap.get(judgeList.get(j).getjEvaluatorRoleId()) * total_score;
//            uid_score_map.put(p_a_u_str,uid_score_map.get(p_a_u_str)==null ? total_score : total_score + uid_score_map.get(p_a_u_str));
//            uid_time_map.put(p_a_u_str,uid_time_map.get(p_a_u_str)==null ? 1 : uid_time_map.get(p_a_u_str) + 1);
//        }
    }

    public List<FeedbackRecord> selectFeedbackByEtorId(int pId, int uId, String start, String end) {

        return judgeDao.selectFeedbackByEtorId(uId, pId, start, end);
    }
}
