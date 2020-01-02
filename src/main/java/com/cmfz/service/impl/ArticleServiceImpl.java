package com.cmfz.service.impl;

import com.cmfz.dao.ArticleDao;
import com.cmfz.entity.Album;
import com.cmfz.entity.Article;
import com.cmfz.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> page(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //总条数
        Integer count = articleDao.count();
        map.put("records", count);
        //计算总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);
        //获取数据
        Integer start = (page - 1) * rows;
        List<Article> page1 = articleDao.page(start, rows);
        map.put("rows", page1);
        map.put("page", page);
        return map;
    }

    @Override
    public void delete(String[] id) {
        articleDao.delete(id);
    }

    @Override
    public void insert(Article article) {
        article.setId(UUID.randomUUID().toString().replace("-",""));
        article.setCreate_date(new Date());
        articleDao.insert(article);
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    //上传 回显图片
    public Map<String, Object> upload(MultipartFile img, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/kindimg");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = img.getOriginalFilename();
        String newName = new Date().getTime() + "_" + filename;
        try {
            img.transferTo(new File(realPath, newName));
            map.put("error", 0);
            String scheme = request.getScheme();//http
            InetAddress localHost = InetAddress.getLocalHost();//IP
            String s = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();//端口号
            String contextPath = request.getContextPath();//项目名
            String url = scheme + "://" + s + ":" + serverPort + contextPath + "/upload/kindimg/" + newName;
            System.out.println(url);
            map.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
   *
   * {
   "moveup_dir_path": "",
   "current_dir_path": "",
   "current_url": "/ke4/php/../attached/",
   "total_count": 5,
   "file_list": [
       {
           "is_dir": false,
           "has_file": false,
           "filesize": 208736,
           "dir_path": "",
           "is_photo": true,
           "filetype": "jpg",
           "filename": "1241601537255682809.jpg",
           "datetime": "2018-06-06 00:36:39"
       }
    ]
}
   * */
    //回显图片空间
    @Override
    public Map<String, Object> getAllImg(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //获取文件夹路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/kindimg");
        //获取文件
        File file = new File(realPath);
        String[] list = file.list();
        List<Object> lis = new ArrayList<>();
        for (String s : list) {
            Map<Object, Object> hashMap = new HashMap<>();
            hashMap.put("is_dir", false);//是否是文件夹
            hashMap.put("has_file", false);//是否是文件
            File file1 = new File(realPath, s);
            long length = file1.length();
            hashMap.put("filesize", length);//文件大小  Long类型
            hashMap.put("dir_path", "");
            hashMap.put("is_photo", true);//是否是图片
            String extension = FilenameUtils.getExtension(s);
            hashMap.put("filetype", extension);
            hashMap.put("filename", s);
            String s1 = s.split("_")[0];
            Long aLong = Long.valueOf(s1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(aLong);
            hashMap.put("datetime", format);
            lis.add(hashMap);
        }
        map.put("file_list", lis);
        map.put("total_count", list.length);
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");

        try {
            String scheme = request.getScheme();
            InetAddress localHost = InetAddress.getLocalHost();
            String s = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String url = scheme + "://" + s + ":" + serverPort + contextPath + "/upload/kindimg/";
            System.out.println(url);
            map.put("current_url", url);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Article SelectById(String id) {
        Article article = articleDao.selectById(id);
        return article;
    }

    @Override
    public List<Article> select() {
        return articleDao.select();
    }
}
