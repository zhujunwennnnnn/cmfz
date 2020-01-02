package com.cmfz;


import com.cmfz.dao.UserDao;
import com.cmfz.entity.User;
import com.cmfz.vo.MapDat;
import com.cmfz.vo.SevenMap;
import io.goeasy.GoEasy;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class userTest {
    @Autowired
    private UserDao userDao;
    @Test
    public void test(){
        userDao.insert(new User(UUID.randomUUID().toString(),"1","123","11","123","123","123","123","123","123",new Date(),new Date(),"123","123","123"));
        List<Integer> integers = new ArrayList<>();
        List<SevenMap> select = userDao.select();
        for (SevenMap map : select) {
            integers.add(map.getCount());
        }

        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("zjw",integers.toString());
    }
    
}
