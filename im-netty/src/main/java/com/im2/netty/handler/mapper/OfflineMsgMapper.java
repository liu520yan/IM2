package com.im2.netty.handler.mapper;

import com.im2.netty.handler.entity.OfflLineMsgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by liuyan on 2018/9/15.
 */
@Mapper
public interface OfflineMsgMapper {
    @Select("select * from offlline_msg shere accept = #{0}")
    List<OfflLineMsgEntity> findEntityByAccept(String accept);

}
