package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.CmsCategory;
import com.lyx.portal.service.CmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 栏目控制器
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CmsCategoryController {

    @Autowired
    private CmsCategoryService cmsCategoryService;

    /**
     * 获取栏目列表
     */
    @GetMapping
    public IPage<CmsCategory> getCategories(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return cmsCategoryService.page(new Page<>(page, size));
    }

    /**
     * 获取栏目详情
     */
    @GetMapping("/{id}")
    public CmsCategory getCategory(@PathVariable Long id) {
        return cmsCategoryService.getById(id);
    }

    /**
     * 创建栏目
     */
    @PostMapping
    public CmsCategory createCategory(@RequestBody CmsCategory category) {
        cmsCategoryService.save(category);
        return category;
    }

    /**
     * 更新栏目
     */
    @PutMapping("/{id}")
    public CmsCategory updateCategory(@PathVariable Long id, @RequestBody CmsCategory category) {
        category.setId(id);
        cmsCategoryService.updateById(category);
        return category;
    }

    /**
     * 删除栏目
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        cmsCategoryService.removeById(id);
    }
}