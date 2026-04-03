package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.SysUserMapper;
import com.lyx.portal.model.SysUser;
import com.lyx.portal.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        // TODO: 返回完整的UserDetails对象，包含权限信息
        return org.springframework.security.core.userdetails.User
                .withUsername(sysUser.getUsername())
                .password(sysUser.getPassword())
                .authorities("ROLE_USER")
                .build();
    }
}