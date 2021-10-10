package com.javamentor.springboot.service;


import com.javamentor.springboot.model.Role;
import com.javamentor.springboot.model.User;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    void addUser(User user);
    void delUser(Long id);
    void updUser(User user, boolean encPass);
    User getUser(Long id);
    void createDataTables();
    Role getRole(String roleName);
}
