package com.cmfz.service.impl;

import com.cmfz.dao.AdminDao;
import com.cmfz.entity.Admin;
import com.cmfz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> queryByUsername(String username, String code, String password , HttpSession session) {
        Admin admin = adminDao.queryByUsername(username);
        String code1 = (String) session.getAttribute("code");
       Map<String, Object> map = new HashMap<>();
        if (code.equals(code1)) {
            if (admin != null) {
                if (admin.getPassword().equals(password)) {
                    System.out.println("ok");
                    session.setAttribute("Admin", admin);
                    map.put("struts","success");
                    return map;
                }
                map.put("struts","error");
                return map;
            }
            map.put("struts","error");
            return map;
        } else {
            map.put("struts","codeerror");
            return map;
        }
    }
}
