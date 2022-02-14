package com.example.myblog.service.impl;

import com.example.myblog.entity.MBlog;
import com.example.myblog.mapper.MBlogMapper;
import com.example.myblog.service.IMBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class MBlogServiceImpl extends ServiceImpl<MBlogMapper, MBlog> implements IMBlogService {

}
