package com.cmfz.controller;


import com.cmfz.entity.Album;
import com.cmfz.entity.Chapter;
import com.cmfz.service.AlbumService;
import com.cmfz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;


    @RequestMapping("page")
    public Map<String, Object> page(Integer page, Integer rows, String id) {
        Map<String, Object> map = chapterService.page(page, rows, id);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Chapter chapter, String[] id, String albumId) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            //添加数据
            chapter.setAlbum_id(albumId);
            chapter.setId(UUID.randomUUID().toString().replace("-",""));
            String insert = chapterService.insert(chapter);
            //查询数量
            Integer count = chapterService.count();
            Album album = new Album();
            album.setId(albumId);
            album.setCounts(String.valueOf(count));
            albumService.update(album);

            if ("ok".equals(insert)) {
                map.put("chapterId", chapter.getId());
                map.put("msg", "添加成功");
            } else {
                map.put("msg", "添加失败");
            }
        } else if ("edit".equals(oper)) {
            String update = null;
            if (chapter.getSrc() == "") {
                chapter.setSrc(null);
                update = chapterService.update(chapter);
            } else {
                update = chapterService.update(chapter);
            }
            if ("ok".equals(update)) {
                map.put("chapterId", chapter.getId());
                map.put("msg", "修改成功");
            } else {
                map.put("msg", "修改失败");
            }
        } else if ("del".equals(oper)) {
            Chapter ch = chapterService.selectById(id[0]);
            System.out.println(id[0]);
            String delete = chapterService.delete(id);
            if ("ok".equals(delete)) {
                String album_id = ch.getAlbum_id();
                Integer count = chapterService.count();
                Album album = new Album();
                album.setId(album_id);
                album.setCounts(String.valueOf(count));
                albumService.update(album);
                map.put("msg", "删除成功");
            } else {
                map.put("msg", "删除失败");
            }
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile src, HttpSession session, String chapterId) {
        chapterService.upload(src, session, chapterId);
    }

    @RequestMapping("value")
    public Chapter value(String id) {
        Chapter chapter = chapterService.selectById(id);
        return chapter;
    }

    @RequestMapping("download")
    public String download(String src, HttpServletRequest request, HttpServletResponse response) {
        String download = chapterService.download(src, response, request);
        System.out.println(download);
        return null;
    }
}
