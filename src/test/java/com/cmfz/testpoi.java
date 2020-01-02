package com.cmfz;


import com.cmfz.dao.AdminDao;
import com.cmfz.dao.UserDao;
import com.cmfz.entity.Admin;
import com.cmfz.entity.User;
import com.cmfz.vo.MapDat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class testpoi {
    @Autowired
    private UserDao userDao;



    @Test
    public void test1() {
        //创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作簿
        HSSFSheet sheet = workbook.createSheet("nihao");
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        //单元格设置
        cell.setCellValue("你好");
        cell1.setCellValue(666);

        try {
            workbook.write(new File("E:/nihao.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test2() throws IOException {
        //创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //字体样式
        HSSFFont font = workbook.createFont();
        font.setBold(true);//加粗
        font.setColor(Font.COLOR_RED);
        font.setFontHeightInPoints((short) 20);
        //日期格式
        HSSFDataFormat format = workbook.createDataFormat();
        short format1 = format.getFormat("yyyy-MM-dd");

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);//字体样式载入
        cellStyle.setDataFormat(format1);//日期格式载入
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//单元格居中
        //创建工作簿
        HSSFSheet sheet = workbook.createSheet("哈喽");
        sheet.setColumnWidth(2, 70 * 256);
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellStyle(cellStyle);
        //单元格设值
        cell.setCellValue("zp");
        cell1.setCellValue(250);
        cell2.setCellValue(new Date());
        //输出
        workbook.write(new File("E:/test1.xls"));

    }

    @Test
    public void test3() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("hello");
        HSSFRow row = sheet.createRow(0);
        String[] ids = {"ID","账号","密码"};
        for (int i = 0; i < ids.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ids[i]);
        }


        try {
            workbook.write(new File("E:/admin.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test4(){
        List<MapDat> mapDats = userDao.selectByMonth();
        /*for (MapDat mapDat : mapDats) {
            System.out.println(mapDat);
        }*/
        System.out.println(mapDats);
    }


}
