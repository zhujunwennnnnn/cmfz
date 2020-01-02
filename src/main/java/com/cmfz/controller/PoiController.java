package com.cmfz.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.cmfz.entity.Admin;
import com.cmfz.entity.Banner;
import com.cmfz.service.BannerService;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("poi")
public class PoiController {
    @Autowired
    private BannerService bannerService;

    //导出
    @RequestMapping("outPoi")
    public void outPoi(HttpSession session){
        //1、创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFFont font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBold(true);
        font.setFontName("楷体");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        //2、创建工作簿
        HSSFSheet sheet = workbook.createSheet("管理员信息");
        //3、创建行
        HSSFRow row = sheet.createRow(0);
        //4、创建标题
        String[] titles ={"编号","账号","密码"};
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }
        Admin admin = (Admin) session.getAttribute("Admin");
        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(admin.getId());
        row1.createCell(1).setCellValue(admin.getUsername());
        row1.createCell(2).setCellValue(admin.getPassword());

        try {
            workbook.write(new File("E:/admin.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //easy导出
    @RequestMapping("easyOutPoi")
    public void easyOutPoi(){
        List<Banner> banners = bannerService.selectAll();
        System.out.println(banners);
        for (Banner banner : banners) {
            banner.setImg("D:\\GOOD\\IDEAWorkSpace\\springboot\\cmfz\\src\\main\\webapp\\upload\\img\\"+banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图展示", "图一"), Banner.class, banners);

        try {
            workbook.write(new FileOutputStream("E:/banners.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

