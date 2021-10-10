package com.javamentor.springboot.dao;

import com.javamentor.springboot.model.Role;
import com.javamentor.springboot.model.User;
import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void addUser(User user);
    void delUser(Long id);
    void updUser(User user);
    User getUser(Long id);
    User getUserByName(String s);
    void createDataTables();
    Role getRole(String roleName);
}

