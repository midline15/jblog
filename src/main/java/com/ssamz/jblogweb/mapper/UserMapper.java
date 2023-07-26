package com.ssamz.jblogweb.mapper;

import com.ssamz.jblogweb.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS(ID, USERNAME, PASSWORD, EMAIL) VALUES((SELECT NVL(MAX(ID), 0) + 1 FROM USERS), #{username}, #{password}, #{email})")
    public void insertUser(User user);

    @Update("UPDATE USERS PASSWORD = #{password}, EMAIL = #{email} WHERE ID = #{id}")
    public void updateUser(User user);

    @Delete("DELETE USERS WHERE ID = #{id}")
    public void deleteUser(User user);

    @Select("SELECT * FROM USERS WHERE USERNAME = #{username}")
    public void getUser(User user);

    @Select("SELECT * FROM USERS ORDER BY USERNAME DESC")
    public List<User> getUserList();
}
