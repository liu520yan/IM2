package com.im2.netty.handler.entity;

import com.im2.common.constants.AcceptType;
import lombok.Data;

/**
 * 离线消息实体类
 * Created by liuyan on 2018/9/14.
 */
@Data
public class OfflLineMsgEntity {
    private String sender; //发送者
    private String accept; //接收消息的人的电话号码
    private AcceptType acceptType; //接收消息的对象的类型 个人/组织
    private Long time;//时间
    private String content;  //内容
}
