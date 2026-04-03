package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.SysRole;
import com.lyx.portal.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色控制器
 */
@RestController
@RequestMapping("/api/v1/roles")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取角色列表
     */
    @GetMapping
    public IPage<SysRole> getRoles(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return sysRoleService.page(new Page<>(page, size));
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    public SysRole getRole(@PathVariable Long id) {
        return sysRoleService.getById(id);
    }

    /**
     * 创建角色
     */
    @PostMapping
    public SysRole createRole(@RequestBody SysRole role) {
        sysRoleService.save(role);
        return role;
    }

    /**
     * 更新角色
     */
    @PutMapping("/{id}")
    public SysRole updateRole(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        sysRoleService.updateById(role);
        return role;
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        sysRoleService.removeById(id);
    }
}