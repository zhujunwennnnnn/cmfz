package com.cmfz.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {
    @Excel(name="编号")
    private String id;
    @Excel(name="标题")
    private String title;
    @Excel(name="轮播图",type=2,width = 100,height = 50)
    private String img;
    @Excel(name="创建日期",format = "yyyy-MM-dd")
    private Date create_date;
    @Excel(name="状态")
    private String status;
    @Excel(name="预留字段")
    private String other;
}
