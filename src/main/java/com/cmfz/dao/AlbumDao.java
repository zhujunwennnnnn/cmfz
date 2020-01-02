package com.cmfz.dao;

import com.cmfz.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //增
    void insert(Album album);
    //删除
    void delete(String[] id);
    //修改
    void update(Album album);
    //分页查
    List<Album> page(@Param("start")int start,@Param("rows")int rows);
    //数量
    Integer count();

    List<Album> select();

    Album selectById(String id);
}
