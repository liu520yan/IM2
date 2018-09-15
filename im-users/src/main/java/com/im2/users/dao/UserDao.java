package com.im2.users.dao;

import com.im2.common.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by liuyan on 2018/8/23.
 */
@Mapper
public interface UserDao {
    @Select("select * from user where username = #{0} and password = #{1}")
    UserEntity findUserByNameAndPsd(String username, String password);

    @Insert("insert into user(username, password, phone) values(#{username}, #{password} ,#{phone})")
    int insertByUser(UserEntity user);

    @Select("select * from user where username = #{0}")
    UserEntity findUserByName(String username);

    @Select("select * from user where phone = #{0}")
    UserEntity findUserPhone(String phone);

    @Delete("delete from user where phone = #{0}")
    void deleteByPhone(String phone);
}
