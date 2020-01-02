package com.cmfz.dao;

import com.cmfz.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //增
    void insert(Banner banner);
    //删
    void delete(String[] ids);
    //改
    void update(Banner banner);
    //分页查
    List<Banner> select(@Param("start") Integer start , @Param("rows") Integer rows);
    //查数量
    Integer selectByContent();
    //查
    List<Banner> selectAll();

}
