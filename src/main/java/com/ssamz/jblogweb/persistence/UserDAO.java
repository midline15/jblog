package com.ssamz.jblogweb.persistence;

import com.ssamz.jblogweb.domain.User;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserDAO {

    private final SqlSessionTemplate mybatis;

    public void insertUser(User user) {
        mybatis.insert("insertUser.html", user);
    }

    public void updateUser(User user) {
        mybatis.update("updateUser", user);
    }

    public void deleteUser(User user) {
        mybatis.delete("deleteUser", user);
    }

    public User getUser(User user) {
        return mybatis.selectOne("getUser", user);
    }

    public List<User> getUserList() {
        return mybatis.selectList("getUserList");
    }
}
