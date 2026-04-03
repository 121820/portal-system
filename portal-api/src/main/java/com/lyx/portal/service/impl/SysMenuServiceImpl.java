package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.SysMenuMapper;
import com.lyx.portal.model.SysMenu;
import com.lyx.portal.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单服务实现类
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}