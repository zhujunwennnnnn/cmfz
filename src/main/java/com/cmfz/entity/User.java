package com.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;
    private String phone_number;
    private String password;
    private String name;
    private String dharma;
    private String head_img;
    private String sex;
    private String address;
    private String sign;
    private String guru_id;
    private Date last_date;
    private Date create_date;
    private String status;
    private String salt;
    private String other;
}
