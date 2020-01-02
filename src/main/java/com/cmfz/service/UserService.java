package com.cmfz.service;

import com.cmfz.entity.User;
import com.cmfz.vo.MapDat;
import com.cmfz.vo.Mapmap;
import com.cmfz.vo.SevenMap;

import java.util.List;
import java.util.Map;

public interface UserService {

    void insert(User user);

    void update(User user);

    List<SevenMap> select();

    List<MapDat> selectByMonth();

    List<Mapmap> selectMap();

    Map<String ,Object> page(Integer start, Integer rows);

}
