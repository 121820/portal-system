package com.lyx.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.portal.model.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户服务接口
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名获取用户
     */
    SysUser getByUsername(String username);

    /**
     * 加载用户详情（用于Spring Security）
     */
    UserDetails loadUserByUsername(String username);
}