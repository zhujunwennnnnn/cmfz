package com.cmfz.controller;

import com.cmfz.entity.Album;
import com.cmfz.entity.Article;
import com.cmfz.entity.Banner;
import com.cmfz.entity.Chapter;
import com.cmfz.service.AlbumService;
import com.cmfz.service.ArticleService;
import com.cmfz.service.BannerService;
import com.cmfz.service.ChapterService;
import com.cmfz.vo.BannerFirst;
import com.cmfz.vo.BodyFirst;
import com.cmfz.vo.Introduction;
import com.cmfz.vo.Zrows;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FirstController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;


    @RequestMapping("first_page")
    public List<Map<String, Object>> first_page(HttpServletRequest request, String type, String uid,String sub_type) {

        String scheme = request.getScheme();//http
        InetAddress localHost = null;//IP
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String s = localHost.toString().split("/")[1];
        String contextPath = request.getContextPath();//项目名
        List<BannerFirst> bannerFirsts = new ArrayList<>();
        List<BodyFirst> bodyFirsts = new ArrayList<>();
        if ("all".equals(type)) {
            List<Banner> banners = bannerService.selectAll();
            for (Banner banner : banners) {
                BannerFirst bannerFirst = new BannerFirst();
                String url = scheme + "://" + s + ":" + contextPath + "/upload/img/" + banner.getImg();
                bannerFirst.setThumbnail(url);
                bannerFirst.setDesc(banner.getTitle());
                bannerFirst.setId(banner.getId());
                bannerFirsts.add(bannerFirst);
            }
            List<Album> select = albumService.select();
            for (Album album : select) {
                BodyFirst bodyFirst = new BodyFirst();
                String url = scheme + "://" + s + ":" + contextPath + "/upload/img/" + album.getImg();
                bodyFirst.setThumbnail(url);
                bodyFirst.setTitle(album.getTitle());
                bodyFirst.setAuthor(album.getAuthor());
                bodyFirst.setType("0");
                Integer counts = chapterService.counts(album.getId());
                bodyFirst.setSet_count(counts);
                bodyFirst.setCreate_date(album.getCreate_date());
                bodyFirsts.add(bodyFirst);
            }
            List<Article> select1 = articleService.select();
            for (Article article : select1) {
                BodyFirst bodyFirst = new BodyFirst();
                bodyFirst.setThumbnail("");
                bodyFirst.setCreate_date(article.getCreate_date());
                bodyFirst.setSet_count(null);
                bodyFirst.setAuthor(article.getAuthor());
                bodyFirst.setType("1");
                bodyFirst.setTitle(article.getTitle());
                bodyFirsts.add(bodyFirst);
            }

        }


        if (type.equals("wen")) {
            List<Album> select = albumService.select();
            for (Album album : select) {
                BodyFirst bodyFirst = new BodyFirst();
                String url = scheme + "://" + s + ":" + contextPath + "/upload/img/" + album.getImg();
                bodyFirst.setThumbnail(url);
                bodyFirst.setTitle(album.getTitle());
                bodyFirst.setAuthor(album.getAuthor());
                bodyFirst.setType("0");
                Integer counts = chapterService.counts(album.getId());
                bodyFirst.setSet_count(counts);
                bodyFirst.setCreate_date(album.getCreate_date());
                bodyFirsts.add(bodyFirst);
            }
        }
        if (type.equals("si")) {
            List<Article> select = articleService.select();
            for (Article article : select) {
                BodyFirst bodyFirst = new BodyFirst();
                bodyFirst.setThumbnail("");
                bodyFirst.setCreate_date(article.getCreate_date());
                bodyFirst.setSet_count(null);
                bodyFirst.setAuthor(article.getAuthor());
                bodyFirst.setType("1");
                bodyFirst.setTitle(article.getTitle());
                bodyFirsts.add(bodyFirst);
            }
        }

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("header", bannerFirsts);
        hashMap.put("body", bodyFirsts);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(hashMap);

        return list;
    }

    @RequestMapping("wen")
    public  Map<String, Object> wen(String uid ,String id,HttpServletRequest request){
        Map<String, Object> hashMap = new HashMap<>();

        Album album = albumService.selectById(id);
        Introduction introduction = new Introduction();
        introduction.setThumbnail("");
        introduction.setTitle(album.getTitle());
        introduction.setScore(album.getScore());
        introduction.setAuthor(album.getAuthor());
        introduction.setBroadcast(album.getBroadcaster());
        Integer counts = chapterService.counts(id);
        introduction.setSet_count(counts);
        introduction.setBrief(album.getBrief());
        introduction.setCreate_date(album.getCreate_date());
        hashMap.put("introduction",introduction);

        List<Chapter> chapters = chapterService.selectByAlbum(id);
        List<Zrows> zrows1 = new ArrayList<>();
        String scheme = request.getScheme();//http
        for (Chapter chapter : chapters) {
            Zrows zrows = new Zrows();
            zrows.setDownload_url(scheme+"/upload/music/"+ chapter.getSrc());
            zrows.setDuration(chapter.getDuration());
            zrows.setSize(chapter.getSizes());
            zrows.setTitle(chapter.getTitle());
            zrows1.add(zrows);
        }
        hashMap.put("list",zrows1);
        return hashMap;

    }

}
