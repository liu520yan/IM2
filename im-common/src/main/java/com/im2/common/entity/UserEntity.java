package com.im2.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuyan on 2018/8/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String phone;
}
