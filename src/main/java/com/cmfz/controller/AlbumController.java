package com.cmfz.controller;

import com.cmfz.entity.Album;
import com.cmfz.entity.Banner;
import com.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("page")
    public Map<String, Object> page(int page, int rows) {
        return albumService.page(page, rows);
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Album album, String[] id) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            album.setId(UUID.randomUUID().toString().replace("-",""));
            album.setCounts("0");
            String insert = albumService.insert(album);
            if ("ok".equals(insert)) {
                map.put("albumId", album.getId());
                map.put("msg", "添加成功");
            } else {
                map.put("msg", "添加失败");
            }
        } else if ("edit".equals(oper)) {

            if (album.getImg() == "") {
                System.out.println(album);
                albumService.update(album);
            } else {
                map.put("albumId", album.getId());
                albumService.update(album);
            }
                map.put("msg", "添加成功");

        } else if ("del".equals(oper)) {
            String delete = albumService.delete(id);
            if ("ok".equals(delete)) {
                map.put("msg", "删除成功");
            } else {
                map.put("msg", "删除失败");
            }
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile img, String albumId, HttpSession session) {
        albumService.upload(img, session, albumId);
    }

}
