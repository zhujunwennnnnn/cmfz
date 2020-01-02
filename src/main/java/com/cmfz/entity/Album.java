package com.cmfz.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//专辑
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    private String id;
    private String title;
    private String img;
    private String score;
    private String author;
    private String broadcaster;
    private String counts;
    private String brief;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_date;
    private String status;
    private String other;
}
