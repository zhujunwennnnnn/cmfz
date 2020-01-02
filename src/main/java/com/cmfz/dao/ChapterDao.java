package com.cmfz.dao;

import com.cmfz.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    void insert(Chapter chapter);
    void delete(String[] id);
    void update(Chapter chapter);
    List<Chapter> page(@Param("start")Integer start , @Param("rows")Integer rows,@Param("album_id")String album_id);
    Integer count();
    Integer counts(String album_id);
    Chapter selectById(String id);
    List <Chapter> selectByAlbum(String id);
}
