package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.SysRoleMapper;
import com.lyx.portal.model.SysRole;
import com.lyx.portal.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现类
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}