package com.cmfz.service.impl;

import com.cmfz.dao.UserDao;
import com.cmfz.entity.User;
import com.cmfz.service.UserService;
import com.cmfz.vo.MapDat;
import com.cmfz.vo.Mapmap;
import com.cmfz.vo.SevenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public List<MapDat> selectByMonth() {
        return userDao.selectByMonth();
    }

    @Override
    public Map<String, Object> page(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer count = userDao.count();
        map.put("records", count);
        //总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);
        //获取数据
        Integer start = (page - 1) * rows;
        List<User> page1 = userDao.page(start, rows);
        map.put("rows", page1);

        map.put("page", page);


        return map;
    }

    @Override
    public List<Mapmap> selectMap() {
        return userDao.selectMap();
    }

    @Override
    public List<SevenMap> select() {
        return userDao.select();
    }
}
