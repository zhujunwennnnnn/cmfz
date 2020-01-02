package com.cmfz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyFirst implements Serializable {
    private String thumbnail;
    private String title;
    private String author;
    private String type;
    private Integer set_count;
    private Date create_date;

}
