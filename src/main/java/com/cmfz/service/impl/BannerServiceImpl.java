package com.cmfz.service.impl;

import com.cmfz.dao.BannerDao;
import com.cmfz.entity.Banner;
import com.cmfz.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Transactional
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            System.out.println(id);
        }
        bannerDao.delete(ids);
    }

    @Override
    public void update(Banner banner) {
        System.out.println("service____"+banner);
        if(banner.getImg()==""){
            banner.setImg(null);
            bannerDao.update(banner);
        }
        bannerDao.update(banner);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> select(Integer page , Integer rows){
        Map<String, Object> map = new HashMap<>();
        //总条数
        Integer allpage = bannerDao.selectByContent();
        map.put("records",allpage);
        //总页数
        Integer total = allpage % rows ==0 ? allpage /rows : allpage / rows+1;
        map.put("total",total);
        //计算起始页
        Integer start = (page-1)*rows;
        List<Banner> select = bannerDao.select(start,rows);
        map.put("rows",select);
        //当前页
        map.put("page",page);
        return map;
    }

    @Override
    public List<Banner> selectAll() {
        return bannerDao.selectAll();
    }

    @Override
    public void upload(MultipartFile img, String bannerId, HttpSession session) {
        //获取upload路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //获取文件的名字
        String filename = img.getOriginalFilename();
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            img.transferTo(new File(realPath, filename));
        } catch (IOException e) {

        }
        Banner banner = new Banner();
        banner.setImg(filename);
        banner.setId(bannerId);
        bannerDao.update(banner);
    }
}
