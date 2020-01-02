package com.cmfz.controller;


import com.cmfz.entity.Admin;
import com.cmfz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map<String, Object> login(String username, String password, String code, HttpSession session) {
        Map<String, Object> map = adminService.queryByUsername(username, code, password, session);
        return map;
    }
}
