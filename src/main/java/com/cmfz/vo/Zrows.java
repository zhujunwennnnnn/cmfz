package com.cmfz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zrows implements Serializable {
    private String title;
    private String download_url;
    private String size;
    private String duration;
}
