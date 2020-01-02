package com.cmfz.controller;

import com.cmfz.dao.BannerDao;
import com.cmfz.entity.Banner;
import com.cmfz.service.BannerService;
import com.cmfz.vo.BannerFirst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    @RequestMapping("page")
    public Map<String, Object> page(int page, int rows) {
        Map<String, Object> map = bannerService.select(page, rows);
        return map;
    }


    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Banner banner, String[] id) {
        Map<String, Object> map = new HashMap<>();
        if (oper.equals("add")) {
            banner.setId(UUID.randomUUID().toString());
            banner.setImg(banner.getImg() + new Date().getTime());
            bannerService.insert(banner);
            map.put("bannerId", banner.getId());
        } else if (oper.equals("edit")) {
            if (banner.getImg() != "") {
                map.put("bannerId", banner.getId());
                bannerService.update(banner);
            } else {
                bannerService.update(banner);
                map.put("bannerId", null);
            }
        } else if ("del".equals(oper)) {
            bannerService.delete(id);
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile img, String bannerId, HttpSession session) {
        bannerService.upload(img, bannerId, session);

    }


    @RequestMapping("pc")
    public Map<String, Object> queryAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("all", bannerService.selectAll());
        map.put("count", bannerDao.selectByContent());
        return map;
    }



}
