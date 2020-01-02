package com.cmfz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Introduction implements Serializable {
    private String thumbnail;
    private String title;
    private String score;
    private String author;
    private String broadcast;
    private Integer set_count;
    private String brief;
    private Date create_date;
}
