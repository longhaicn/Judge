package com.judge.utils;


import java.io.File;
import java.io.IOException;
import java.util.List;
import com.judge.po.ScoreReport;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class POIUtil {


    public static void  writeExcel03(String path,String sheetName, List<ScoreReport> list) throws IOException{
        //创建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        HSSFSheet sheet = workBook.createSheet(sheetName);
        //创建行,第1行
        HSSFRow rowTitle = sheet.createRow(0);
        //创建单元格，操作第1行第n列

        HSSFCell cell = rowTitle.createCell(0, CellType.STRING);
        cell.setCellValue("序号");
        cell = rowTitle.createCell(1, CellType.STRING);
        cell.setCellValue("姓名");
        cell = rowTitle.createCell(2, CellType.STRING);
        cell.setCellValue("得分");
        cell = rowTitle.createCell(3, CellType.STRING);
        cell.setCellValue("项目奖金");
        cell = rowTitle.createCell(4, CellType.STRING);
        cell.setCellValue("工资绩效奖金");
        cell = rowTitle.createCell(5, CellType.STRING);
        cell.setCellValue("职务");
        cell = rowTitle.createCell(6, CellType.STRING);
        cell.setCellValue("项目名称");
        cell = rowTitle.createCell(7, CellType.STRING);
        cell.setCellValue("开始时间");
        cell = rowTitle.createCell(8, CellType.STRING);
        cell.setCellValue("结束时间");

        for (int i = 0 ; i<list.size();i++) {
            ScoreReport scoreReport =list.get(i);
            HSSFRow row = sheet.createRow(i+1);
            //创建单元格，操作第三行第三列
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(i + 1);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(scoreReport.getuUsername());
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue(scoreReport.getEvSum());
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(scoreReport.getuProportion());
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(scoreReport.getuPerformance());
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(scoreReport.getuRole());
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(scoreReport.getpName());
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(scoreReport.getpStart());
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(scoreReport.getpEnd());

        }

        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        workBook.write(new File(path+"\\"+sheetName+".xls"));

        workBook.close();//最后记得关闭工作簿
    }


    public static void  writeExcel03s(String path,String sheetName, List<ScoreReport> list) throws IOException{
        //创建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        HSSFSheet sheet = workBook.createSheet(sheetName);
        //创建行,第1行
        HSSFRow rowTitle = sheet.createRow(0);
        //创建单元格，操作第1行第n列

        HSSFCell cell = rowTitle.createCell(0, CellType.STRING);
        cell.setCellValue("序号");
        cell = rowTitle.createCell(1, CellType.STRING);
        cell.setCellValue("姓名");
        cell = rowTitle.createCell(2, CellType.STRING);
        cell.setCellValue("得分");
        cell = rowTitle.createCell(3, CellType.STRING);
        cell.setCellValue("工资绩效");
        cell = rowTitle.createCell(4, CellType.STRING);
        cell.setCellValue("开始时间");
        cell = rowTitle.createCell(5, CellType.STRING);
        cell.setCellValue("结束时间");

        for (int i = 0 ; i<list.size();i++) {
            ScoreReport scoreReport =list.get(i);
            HSSFRow row = sheet.createRow(i+1);
            //创建单元格，操作第三行第三列
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(i + 1);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(scoreReport.getuUsername());
            cell = row.createCell(2, CellType.NUMERIC);
            cell.setCellValue(scoreReport.getEvSum());
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(scoreReport.getuPerformance());
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(scoreReport.getpStart());
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(scoreReport.getpEnd());

        }
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        workBook.write(new File(path+"\\"+sheetName+".xls"));

        workBook.close();//最后记得关闭工作簿
    }
}