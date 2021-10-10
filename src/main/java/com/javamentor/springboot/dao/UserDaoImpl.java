package com.javamentor.springboot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.javamentor.springboot.model.Role;
import com.javamentor.springboot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u from User u",User.class).getResultList();
    }

    @Override
    public User getUserByName(String s) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u WHERE u.name = :name", User.class);
        query.setParameter("name", s);
        return query.getSingleResult();
    }

    @Override
    public Role getRole(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r from Role r WHERE r.role = :roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }

    @Override
    public void createDataTables() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        entityManager.persist(new User("Tom", passwordEncoder.encode("12345"), new HashSet<Role>(){{add(userRole);}}));
        entityManager.persist(new User("Mike", passwordEncoder.encode("123"), new HashSet<Role>(){{add(userRole); add(adminRole);}}));
        entityManager.persist(new User("Ivan", passwordEncoder.encode("000"), new HashSet<Role>(){{add(adminRole);}}));
    }

}
