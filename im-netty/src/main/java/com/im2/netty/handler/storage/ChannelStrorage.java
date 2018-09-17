package com.im2.netty.handler.storage;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map形式存储key&&channel
 * Created by liuyan on 2018/9/8.
 */
public class ChannelStrorage {
    private static ChannelStrorage ourInstance = new ChannelStrorage();

    private Map<String, Channel> stringChannelMap = new ConcurrentHashMap<>();

    public static ChannelStrorage getInstance() {
        return ourInstance;
    }

    private ChannelStrorage() {
    }

    public void setKeyAndChannel(String userSign, Channel channel) {
        stringChannelMap.put(userSign, channel);
    }

    public Channel getChannelByKey(String userSign) {
        return stringChannelMap.get(userSign);
    }

    public void removeChannel(String userSign) {
        stringChannelMap.remove(userSign);
    }

    public String getUserSignByChannl(Channel channel) {
        if (channel == null) {
            return null;
        }
        return stringChannelMap
                .entrySet()
                .stream()
                .filter(n -> channel.equals(n.getValue()))
                .findAny()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void removeChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        String userSign = getUserSignByChannl(channel);
        if (userSign != null) {
            stringChannelMap.remove(userSign);
        }
    } 
}
