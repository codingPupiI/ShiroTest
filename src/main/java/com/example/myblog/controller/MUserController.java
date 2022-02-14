package com.example.myblog.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.myblog.common.Result;
import com.example.myblog.entity.MUser;
import com.example.myblog.service.IMUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping("/user")
public class MUserController {
    @Autowired
    IMUserService imUserService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        MUser mUser = new MUser();
        mUser = imUserService.getById(1);
        if (mUser != null) {
            return Result.success(mUser);
        } else {
            return Result.fail(mUser);
        }
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody MUser mUser) {
        Assert.isTrue(imUserService.getOne(new QueryWrapper<MUser>().eq("username",mUser.getUsername())) == null
                      , "已存在相同的用户名");
        mUser.setPassword(SecureUtil.md5(mUser.getPassword()));
        mUser.setCreated(LocalDateTime.now());
        mUser.setStatus(0);
        if (imUserService.save(mUser)) {
            return Result.success(mUser);
        } else {
            return Result.fail(mUser);
        }
    }
}
