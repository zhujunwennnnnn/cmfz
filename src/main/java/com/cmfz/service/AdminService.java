package com.cmfz.service;

import com.cmfz.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    Map<String,Object> queryByUsername(String username, String code, String password, HttpSession session);
}
