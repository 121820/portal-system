package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.SysUserMapper;
import com.lyx.portal.model.SysUser;
import com.lyx.portal.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}