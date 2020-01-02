package com.cmfz.dao;

import com.cmfz.entity.Admin;

public interface AdminDao {
    Admin queryByUsername(String username);
}
