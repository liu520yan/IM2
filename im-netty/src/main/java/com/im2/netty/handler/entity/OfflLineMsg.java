package com.im2.netty.handler.entity;

import com.im2.common.constants.AcceptType;
import lombok.Data;

/**
 * Created by liuyan on 2018/9/14.
 */
@Data
public class OfflLineMsg {
    private String sender; //发送者
    private String accept; //接收消息的人 或者 组织  的唯一标准
    private AcceptType acceptType; //接收消息的对象的类型 个人/组织
    private Long time;//时间
    private String content;  //内容
}