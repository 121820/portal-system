package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.SysMenu;
import com.lyx.portal.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单控制器
 */
@RestController
@RequestMapping("/api/v1/menus")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取菜单列表
     */
    @GetMapping
    public IPage<SysMenu> getMenus(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return sysMenuService.page(new Page<>(page, size));
    }

    /**
     * 获取菜单详情
     */
    @GetMapping("/{id}")
    public SysMenu getMenu(@PathVariable Long id) {
        return sysMenuService.getById(id);
    }

    /**
     * 创建菜单
     */
    @PostMapping
    public SysMenu createMenu(@RequestBody SysMenu menu) {
        sysMenuService.save(menu);
        return menu;
    }

    /**
     * 更新菜单
     */
    @PutMapping("/{id}")
    public SysMenu updateMenu(@PathVariable Long id, @RequestBody SysMenu menu) {
        menu.setId(id);
        sysMenuService.updateById(menu);
        return menu;
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        sysMenuService.removeById(id);
    }
}