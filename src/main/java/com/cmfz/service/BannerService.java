package com.cmfz.service;


import com.cmfz.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BannerService {
    //增
    void insert(Banner banner);
    //删
    void delete(String[] ids);
    //改
    void update(Banner banner);
    //查
    Map<String,Object> select(Integer page , Integer rows);
    //查all
    List<Banner> selectAll();
    //上传
    void upload(MultipartFile img, String bannerId, HttpSession session);
}
