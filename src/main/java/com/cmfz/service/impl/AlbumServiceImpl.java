package com.cmfz.service.impl;

import com.cmfz.annotation.AddCache;
import com.cmfz.annotation.ClearCache;
import com.cmfz.dao.AlbumDao;
import com.cmfz.entity.Album;
import com.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @ClearCache
    public String insert(Album album) {

        albumDao.insert(album);
        return "ok";
    }

    @ClearCache
    public String delete(String[] id) {
        albumDao.delete(id);
        return "ok";
    }

    @ClearCache
    public void upload(MultipartFile img, HttpSession session, String albumId) {
        //获取名字
        String filename = img.getOriginalFilename();
        //获取路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //判断文件夹是否存在
        File f = new File(realPath);
        if(!f.exists()){
            f.mkdirs();
        }
        try {
            img.transferTo(new File(realPath,filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setImg(filename);
        album.setId(albumId);
        albumDao.update(album);

    }

    @ClearCache
    public String update(Album album) {
        if(album.getImg()==""){
            album.setImg(null);
            System.out.println(album);
            albumDao.update(album);
        }else {
            albumDao.update(album);
        }
            return "ok";
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCache
    public Map<String, Object> page(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //总条数
        Integer count = albumDao.count();
        map.put("records",count);
        //总页数
        Integer total = count % rows ==0 ? count / rows : count / rows+1;
        map.put("total",total);
        //计算起始页
        Integer start = (page-1)*rows;
        List<Album> list = albumDao.page(start, rows);
        map.put("rows",list);
        //当前页
        map.put("page",page);
        return map;
    }


    @AddCache
    public List<Album> select() {
        return albumDao.select();
    }

    @AddCache
    public Album selectById(String id) {
        return albumDao.selectById(id);
    }
}
