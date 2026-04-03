package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.SysUser;
import com.lyx.portal.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/v1/users")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     */
    @GetMapping
    public IPage<SysUser> getUsers(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return sysUserService.page(new Page<>(page, size));
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public SysUser getUser(@PathVariable Long id) {
        return sysUserService.getById(id);
    }

    /**
     * 创建用户
     */
    @PostMapping
    public SysUser createUser(@RequestBody SysUser user) {
        sysUserService.save(user);
        return user;
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public SysUser updateUser(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        sysUserService.updateById(user);
        return user;
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        sysUserService.removeById(id);
    }
}