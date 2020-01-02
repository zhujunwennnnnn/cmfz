package com.cmfz.dao;

import com.cmfz.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {

    List<Article> page(@Param("start") Integer start, @Param("rows") Integer rows);

    void delete(String[] id);

    Integer count();

    void insert(Article article);

    void update(Article Article);

    Article selectById(String id);

    List<Article> select();

}