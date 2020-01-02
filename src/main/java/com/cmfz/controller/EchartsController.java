package com.cmfz.controller;

import com.cmfz.service.UserService;
import com.cmfz.vo.MapDat;
import com.cmfz.vo.Mapmap;
import com.cmfz.vo.SevenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Autowired
    private UserService userService;

    @RequestMapping("bySeven")
    public List<Integer> content() {
        List<SevenMap> select = userService.select();
        List<Integer> integers = new ArrayList<>();

        for (SevenMap map : select) {
            integers.add(map.getCount());
        }
        return integers;
    }

    @RequestMapping("byMonth")
    public List<Integer> contents() {
        List<Integer> integers = new ArrayList<>();

        List<MapDat> mapDats = userService.selectByMonth();
        for (int i = 1; i <= 12; i++) {
            boolean f = true;
            for (MapDat mapDat : mapDats) {
                if (i == mapDat.getMonth()) {
                    integers.add(mapDat.getCount());
                    f = false;
                }
            }
            if (f) {
                integers.add(0);
            }
        }
        return integers;
    }

    @RequestMapping("map")
    public List<Map<String, Object>> map() {
        List<Map<String, Object>> list = new ArrayList<>();

        List<Mapmap> mapmaps = userService.selectMap();

        for (Mapmap mapmap : mapmaps) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("name",mapmap.getAddress());
            hashMap.put("value",mapmap.getCount());
            list.add(hashMap);
        }
        return list;
    }
}
