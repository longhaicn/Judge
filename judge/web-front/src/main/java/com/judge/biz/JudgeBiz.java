package com.judge.biz;

import com.judge.dao.InfectDao;
import com.judge.dao.JudgeDao;
import com.judge.po.Affair;
import com.judge.po.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("JudgeBiz")
public class JudgeBiz {
    @Autowired
    private JudgeDao judgeDao;

    @Autowired
    private InfectDao infectDao;

    /**
     * 发起评分事件 and 更新回执表
     */
    @Transactional(rollbackFor = Exception.class)
    public void newJudgeList(List<Judge> judgeList){
        for (int i = 0; i < judgeList.size(); i++) {
            judgeDao.insertJudgeObj(judgeList.get(i));
        }
        infectDao.updateScore(judgeList.get(0).getjAffairId(),judgeList.get(0).getjEvaluatorId(),1);
    }
}
