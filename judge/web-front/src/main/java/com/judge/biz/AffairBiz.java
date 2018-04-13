package com.judge.biz;

import com.judge.dao.AffairDao;
import com.judge.dao.InfectDao;
import com.judge.dao.OrgnizationDao;
import com.judge.dao.UserDao;
import com.judge.po.Affair;
import com.judge.po.AffairMiss;
import com.judge.po.Infect;
import com.judge.po.User;
import com.judge.utils.DingTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("AffairBiz")
public class AffairBiz {
    final String INDEX_URL = "https://oa.shenzhenpoly.com:8443/wxapi/wxclientmenu/176ffa5cb22746cd90827649852e3713?appid=b2588c94b06d4045ad4025438489a347&type=2&operation=fromportal&dd_orientation=auto";
    final String MSG_TITLE = "你有一个新的绩效评分，请马上进行评分";
    /*
     * 自动注入UserDao
     */
    @Autowired
    private OrgnizationDao orgnizationDao;

    @Autowired
    private AffairDao affairDao;

    @Autowired
    private InfectDao infectDao;

    @Autowired
    private UserDao userDao;

    /**
     * 发起评分事件
     */
    @Transactional(rollbackFor = Exception.class)
    public void newAffair(Affair affair, int[] userids) {
        affairDao.insertAffairObj(affair);
        for (int i = 0; i < userids.length; i++) {
            Infect infect = new Infect();
            infect.setDatetime(new Date());
            infect.setiAffairId(affair.getaId());
            infect.setiUserId(userids[i]);
            infect.setiScored(0);
            infectDao.insertInfectObj(infect);
        }

        List<User> users = userDao.selectInIds(userids);
        String[] ding_ids = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            ding_ids[i] = users.get(i).getDingId();
        }
        String accessToken = DingTalkService.getAccessToken();
        //发送钉钉消息通知人员评分
        DingTalkService.sendDingMsg(accessToken, ding_ids, MSG_TITLE, affair.getaAffairs(), INDEX_URL);
    }

    /**
     * 截止现在为止，还有效的评分事件
     *
     * @param project_id
     * @return
     */
    public List<Affair> selectAffairIndate(int project_id) {
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return affairDao.selectAffairIndate(project_id, local_sdf.format(new Date()));
    }

    /**
     * 截止现在为止，无效的评分事件
     *
     * @param project_id
     * @return
     */
    public List<Affair> selectAffairOutdate(int project_id) {
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return affairDao.selectAffairOutdate(project_id, local_sdf.format(new Date()));
    }

    /**
     * 截止现在为止，还未完成评分的评分事件
     *
     * @param o_user_id
     * @return
     */
    public List<Affair> selectAffairIndateNotdoneByUserId(int o_user_id) {
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return affairDao.selectAffairIndateNotdoneByUserId(local_sdf.format(new Date()), o_user_id);
    }

    /**
     * 截止现在为止，还有效的评分事件
     *
     * @param o_user_id
     * @return
     */
    public List<Affair> selectAffairIndateByUserId(int o_user_id) {
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return affairDao.selectAffairIndateByUserId(local_sdf.format(new Date()), o_user_id);
    }

    /**
     * 截止现在为止，无效的评分事件
     *
     * @param o_user_id
     * @return
     */
    public List<Affair> selectAffairOutdateByUserId(int o_user_id) {
        SimpleDateFormat local_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return affairDao.selectAffairOutdateByUserId(local_sdf.format(new Date()), o_user_id);
    }

    /**
     * 查询需要评分的对象
     *
     * @param a_id
     * @param u_id
     * @return
     */
    public List<User> selectUsersForJudge(int a_id, int u_id) {
        User user = orgnizationDao.getUserByAIdAndUId(a_id, u_id);
        if (user.getuRole() == 1) {
            //CEO
            return userDao.selectUsersForJudgeCEO(a_id);
        } else {
            return userDao.selectUsersForJudge(a_id, u_id);
        }
    }

    /**
     * 查询一段时间内发起评分数
     *
     * @param project_id
     * @param start
     * @param end
     * @return
     */
    public int countAffairNum(int project_id, String start, String end) {
        return affairDao.countAffairNum(project_id, start, end);
    }

    public List<AffairMiss> missaffair(String start, String end) {

        return affairDao.select(start, end);
    }
}
