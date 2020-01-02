package com.cmfz.controller;

import com.cmfz.entity.Article;
import com.cmfz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("page")
    public Map<String,Object> page(Integer page ,Integer rows){
        Map<String, Object> map = articleService.page(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(String[] id , String oper, Article article){
        Map<String, Object> hashMap = new HashMap<>();
        if("del".equals(oper)){
            articleService.delete(id);
            hashMap.put("msg","删除成功");
        }
        return  hashMap;
    }
    @RequestMapping("add")
    public Map<String,Object> add(Article article){
        Map<String, Object> hashMap = new HashMap<>();
        article.getContent();
        articleService.insert(article);
        hashMap.put("msg","添加成功");
        return hashMap;
    }
    @RequestMapping("update")
    public Map<String,Object> update(Article article){
        System.out.println(article);
        Map<String, Object> hashMap = new HashMap<>();
        articleService.update(article);
        hashMap.put("msg","修改成功");
        return hashMap;
    }
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile img , HttpServletRequest request){
        Map<String, Object> upload = articleService.upload(img, request);
        return upload;
    }
    @RequestMapping("getAllImgs")
    public Map<String, Object> getAllImgs(HttpServletRequest request){
        Map<String, Object> upload = articleService.getAllImg(request);
        return upload;
    }
    @RequestMapping("ById")
    public Article ById(String id){
        Article article = articleService.SelectById(id);
        System.out.println(article);
        return article;
    };

}
