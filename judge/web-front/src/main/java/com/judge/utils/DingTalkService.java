package com.judge.utils;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 */
public class DingTalkService {
    private static final String CORPID = "ding43af2d3ba11e3298";
    private static final String SECRET = "5Cau2ZI476dyA431ttp5qMXwdSdtiqqkfgyjIeq-QMKPINRAuo8FpT4FqtDWZKGJ";

    private static  final String AGENTID = "169154867";

    /**
     * ACCESSTOKEN地址
     */
    public static final String ACCESSTOKENURL = "https://oapi.dingtalk.com/gettoken";


    /**
     * 获取钉钉的accessToken
     * @return
     */
    public static String getAccessToken(){
        String requestUrl = ACCESSTOKENURL + "?corpid="+CORPID+"&corpsecret="+SECRET;
        String result = HttpUtils.doGet(requestUrl);
        String accessToken = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(result);
        String msg = (String)jsonObject.get("errmsg");
        if("ok".equals(msg)){
            accessToken = (String)jsonObject.get("access_token");
        }
        return accessToken;
    }

    /**
     * 调用钉钉接口发送信息给用户
     * @param accessToken
     * @param userids ding_id的数组
     * @param title 标题
     * @param msg 内容
     * @param linkurl 链接
     * @return
     */
    public static JSONObject sendDingMsg(String accessToken,String[] userids,String title,String msg,String linkurl){
        String recordUrl = "https://oapi.dingtalk.com/message/send" + "?access_token="+accessToken;
        JSONObject jsonObject = new JSONObject();
        JSONObject link = new JSONObject();
        String content = Arrays.toString(userids).replaceAll("\\s", "").replaceAll(",", "|");
        content = content.substring(1,content.length()-1);
        link.put("messageUrl",linkurl);
        link.put("picUrl","");
        link.put("title",title);
        link.put("text",msg);

        jsonObject.put("touser",content);
        jsonObject.put("agentid",AGENTID);
        jsonObject.put("msgtype","link");
        jsonObject.put("link",link);
        String result = HttpUtils.doPost(recordUrl,jsonObject,"utf-8");
        JSONObject resutJSON = JSONObject.parseObject(result);
        return resutJSON;
    }

    public static void main(String[] args){
        String accessToken = DingTalkService.getAccessToken();
        String[] ding_ids = {"3066","3294"};
        System.out.println(sendDingMsg(accessToken, ding_ids, "绩效考核2","11XX项目发起了一个评分，请马上评分","https://oa.shenzhenpoly.com:8443/wxapi/wxclientmenu/176ffa5cb22746cd90827649852e3713?appid=b2588c94b06d4045ad4025438489a347&type=2&operation=fromportal&dd_orientation=auto"));
    }
}