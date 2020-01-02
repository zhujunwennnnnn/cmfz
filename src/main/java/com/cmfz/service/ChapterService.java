package com.cmfz.service;

import com.cmfz.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;
;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    String insert(Chapter chapter);
    String delete(String[] id);
    String update(Chapter chapter);
    Map<String,Object> page(Integer page , Integer rows, String album_id);
    void upload(MultipartFile src, HttpSession session,String chapterId);
    String download(String src, HttpServletResponse response , HttpServletRequest request);
    Integer count();
    Integer counts(String album_id);
    Chapter selectById(String id);
    List<Chapter> selectByAlbum(String id);
}
