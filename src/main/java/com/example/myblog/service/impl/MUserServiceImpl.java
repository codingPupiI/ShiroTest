package com.example.myblog.service.impl;

import com.example.myblog.entity.MUser;
import com.example.myblog.mapper.MUserMapper;
import com.example.myblog.service.IMUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-02-07
 */
@Service
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements IMUserService {

}
