package com.example.myblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.common.Result;
import com.example.myblog.entity.MBlog;
import com.example.myblog.service.IMBlogService;
import com.example.myblog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-02-07
 */
@RestController
@RequestMapping("/blog")
public class MBlogController {
    @Autowired
    IMBlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 5);
        IPage iPage = blogService.page(page, new QueryWrapper<MBlog>().orderByDesc("created"));
        return Result.success(iPage);
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        MBlog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody MBlog mBlog) {
        MBlog temp = null;
        if (mBlog.getId() != null) {
            temp = blogService.getById(mBlog.getId());
            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑别人的博客");
        } else {
            temp = new MBlog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(mBlog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }

    @RequiresAuthentication
    @PostMapping("blogDel/{id}")
    public Result delete(@PathVariable Long id) {
        Assert.isTrue(blogService.getById(id).getUserId() == ShiroUtil.getProfile().getId()
                      , "没有权限编辑别人的博客");
        if (blogService.removeById(id)) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }
}
