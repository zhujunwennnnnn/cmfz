package com.cmfz.service;

import com.cmfz.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    //增
    String insert(Album album);
    //删除
    String delete(String[] id);
    //修改
    String update(Album album);
    //分页查
    Map<String, Object> page(Integer page, Integer rows);
    //上传
    void upload(MultipartFile file, HttpSession session,String albumId);

    List<Album> select();

    Album selectById(String id);
}
