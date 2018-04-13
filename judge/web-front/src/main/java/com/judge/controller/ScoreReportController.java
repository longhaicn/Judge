package com.judge.controller;

import com.judge.biz.ScoreReportBiz;
import com.judge.po.ScoreReport;
import com.judge.utils.JsonUtils;
import com.judge.utils.ListObject;
import com.judge.utils.ResponseUtils;
import com.judge.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScoreReportController {


    @Autowired
    @Qualifier("scoreReportBiz")
    private ScoreReportBiz scoreReportBiz;

    /*
     * 获取指定id的用户
     */


    @RequestMapping(value = "/scorereports")
    public void getScoreReprot(HttpServletRequest request, HttpServletResponse response,String start, String end) {
        try {
            List<ScoreReport> list = scoreReportBiz.getScoreReprots(start, end);
            ListObject listObject = new ListObject();
            listObject.setData(list);
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("success");
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        } catch (Exception e) {
            e.printStackTrace();
            ListObject listObject = new ListObject();
            listObject.setData(null);
            listObject.setCode(StatusCode.CODE_ERROR);
            listObject.setDesc("desc:" + e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        }
    }


    @RequestMapping(value = "/scorereportbyid")
    public void getScoreReprot(HttpServletRequest request, HttpServletResponse response,String projectId,String uId, String start, String end) {
        try {
            int pId = Integer.parseInt(projectId);
            int uid = Integer.parseInt(uId);
            List<ScoreReport> list =  new ArrayList<ScoreReport>();
            list.add( scoreReportBiz.getScoreReprotById(pId,uid,start, end));
            ListObject listObject = new ListObject();
            listObject.setData(list);
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("success");
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        } catch (Exception e) {
            e.printStackTrace();
            ListObject listObject = new ListObject();
            listObject.setData(null);
            listObject.setCode(StatusCode.CODE_ERROR);
            listObject.setDesc("desc:" + e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        }
    }
    @RequestMapping(value = "/scorereport")
    public void getScoreReprot(HttpServletRequest request, HttpServletResponse response,String projectId, String start, String end) {
        try {
            int id = Integer.parseInt(projectId);
            List<ScoreReport> list = scoreReportBiz.getScoreReprot(id, start, end);
            ListObject listObject = new ListObject();
            listObject.setData(list);
            listObject.setCode(StatusCode.CODE_SUCCESS);
            listObject.setDesc("success");
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        } catch (Exception e) {
            e.printStackTrace();
            ListObject listObject = new ListObject();
            listObject.setData(null);
            listObject.setCode(StatusCode.CODE_ERROR);
            listObject.setDesc("desc:" + e.getMessage());
            ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
        }
    }

    @RequestMapping(value = "/getXLS")
    public void getScoreReprot(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            Resource resource = new ClassPathResource("/");
            String ctxPath = null;
            try {
                ctxPath = resource.getFile().getParentFile().getParentFile().getPath() + File.separator + "temp";
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(ctxPath);
            if(!file.exists()){//判断是否待删除目录是否存在
                System.err.println("The dir are not exists!");
                return;
            }
            String[] content = file.list();//取得当前目录下所有文件和文件夹

            String downLoadPath = ctxPath.endsWith(File.separator)?ctxPath:(ctxPath+File.separator)+ content[0];

            long fileLength = new File(downLoadPath).length();

            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(content[0].getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}