package com.example.myblog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 防止密码等隐私数据泄露
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String avatar;
    private String email;
}
