package com.cmfz.service;

import com.cmfz.entity.Album;
import com.cmfz.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface ArticleService {
    Map<String,Object> page(Integer page , Integer rows);

    void delete(String[] id);

    void insert(Article article);

    void update(Article article);

    Map<String,Object> upload(MultipartFile img, HttpServletRequest request);

    Map<String ,Object> getAllImg(HttpServletRequest request);

    Article SelectById(String id);

    List<Article> select();
}
