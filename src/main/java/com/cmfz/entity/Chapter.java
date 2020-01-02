package com.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//章节
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
    private String id;
    private String title;
    private String album_id;
    private String sizes;
    private String duration;
    private String src;
    private String status;
    private String other;
}
