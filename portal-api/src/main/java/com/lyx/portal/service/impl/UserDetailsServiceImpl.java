package com.lyx.portal.service.impl;

import com.lyx.portal.mapper.SysRoleMapper;
import com.lyx.portal.mapper.SysRoleMenuMapper;
import com.lyx.portal.mapper.SysUserRoleMapper;
import com.lyx.portal.model.SysRole;
import com.lyx.portal.model.SysRoleMenu;
import com.lyx.portal.model.SysUser;
import com.lyx.portal.model.SysUserRole;
import com.lyx.portal.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = ((SysUserServiceImpl) sysUserService).getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 获取用户角色
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper.wrap(SysUserRole.class)
                .eq("user_id", sysUser.getId())
        );

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 添加角色权限
        for (SysUserRole userRole : userRoles) {
            SysRole role = sysRoleMapper.selectById(userRole.getRoleId());
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().toUpperCase()));
                
                // 获取角色对应的菜单权限
                List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(
                    com.baomidou.mybatisplus.core.conditions.query.QueryWrapper.wrap(SysRoleMenu.class)
                        .eq("role_id", role.getId())
                );
                
                // 添加菜单权限
                for (SysRoleMenu roleMenu : roleMenus) {
                    // 这里需要根据menuId查询具体的permission字段
                    // 简化处理：假设菜单ID就是权限标识
                    authorities.add(new SimpleGrantedAuthority("MENU_" + roleMenu.getMenuId()));
                }
            }
        }

        // 如果没有分配角色，默认给USER角色
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
    }
}