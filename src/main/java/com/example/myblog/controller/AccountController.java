package com.example.myblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myblog.common.Result;
import com.example.myblog.entity.MUser;
import com.example.myblog.entity.dto.LoginDto;
import com.example.myblog.service.IMUserService;
import com.example.myblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    IMUserService imUserService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        MUser mUser = imUserService.getOne(new QueryWrapper<MUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(mUser,"用户名不存在");
        if (!mUser.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码不正确");
        } else {
            String jwt = jwtUtils.generateToken(mUser.getId());
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-control-Expose-Headers", "Authorization");
            return Result.success(MapUtil.builder()
                            .put("id", mUser.getId())
                            .put("username", mUser.getUsername())
                            .put("avatar", mUser.getAvatar())
                            .put("email", mUser.getEmail())
                            .map());
        }
    }

    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
