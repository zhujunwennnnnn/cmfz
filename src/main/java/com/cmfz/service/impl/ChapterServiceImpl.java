package com.cmfz.service.impl;

import com.cmfz.annotation.AddCache;
import com.cmfz.annotation.ClearCache;
import com.cmfz.dao.AlbumDao;
import com.cmfz.dao.ChapterDao;
import com.cmfz.entity.Album;
import com.cmfz.entity.Chapter;
import com.cmfz.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;

    @ClearCache
    public String insert(Chapter chapter) {
        chapterDao.insert(chapter);
        return "ok";
    }


    @ClearCache
    public String delete(String[] id) {
        chapterDao.delete(id);
        return "ok";
    }

    @ClearCache
    public String update(Chapter chapter) {
        chapterDao.update(chapter);
        return "ok";
    }

    @AddCache
    public Integer count() {
        return chapterDao.count();
    }

    @AddCache
    public Integer counts(String album_id) {
        return chapterDao.counts(album_id);
    }

    @AddCache
    public Chapter selectById(String id) {
        return chapterDao.selectById(id);
    }

    @AddCache
    public List<Chapter> selectByAlbum(String id) {
        return chapterDao.selectByAlbum(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCache
    public Map<String, Object> page(Integer page, Integer rows, String album_id) {
        Map<String, Object> map = new HashMap<>();
        //获取总条数
        Integer count = chapterDao.count();
        map.put("records", count);
        //获取总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);

        //计算起始页
        Integer start = (page - 1) * rows;
        List<Chapter> list = chapterDao.page(start, rows, album_id);
        map.put("rows", list);
        //当前页
        map.put("page", page);
        return map;
    }


    @ClearCache
    public void upload(MultipartFile src, HttpSession session, String chapterId) {
        Chapter chapter = new Chapter();
        //获取文件名字
        String filename = src.getOriginalFilename();
        String newName = filename + "_" + new Date().getTime();
        //获取路径
        String realPath = session.getServletContext().getRealPath("/upload/music");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File mf = new File(realPath, newName);
        try {
            src.transferTo(mf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        try {
            m = encoder.getInfo(mf);  //这里传入的是文件对象
            ls = m.getDuration() / 1000;  //得到一个long类型的时长
        } catch (Exception e) {
            System.out.println("获取音频时长有误：" + e.getMessage());
        }
        chapter.setDuration(String.valueOf(ls / 60) + ":" + String.valueOf(ls % 60)); //转换为分钟:秒
        //获取文件大小
        //file.length可以获取文件字节大小
        DecimalFormat format = new DecimalFormat("0.00");
        String format1 = format.format(new BigDecimal(mf.length() / 1024.0 / 1024));
        //System.out.println(format1);
        String size = String.valueOf(format1) + "MB";
        chapter.setSizes(size);
        chapter.setId(chapterId);
        chapter.setSrc(newName);
        chapterDao.update(chapter);
    }

    @ClearCache
    public String download(String src, HttpServletResponse response, HttpServletRequest request) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //设计下载方式
            String newN = src.split("_")[0];
            String encode = URLEncoder.encode(newN, "utf-8");
            response.setHeader("content-disposition", "attachment;fileName=" + encode);
            //获取路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/music");
            //获取文件输入流
            fileInputStream = new FileInputStream(new File(realPath, src));
            //获取响应输出流
            outputStream = response.getOutputStream();

            //文件传输  读取过程中给Client响应
            byte[] bytes = new byte[2048];
            while (true) {
                int read = fileInputStream.read(bytes, 0, bytes.length);
                if (read == -1) break;
                outputStream.write(bytes, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
