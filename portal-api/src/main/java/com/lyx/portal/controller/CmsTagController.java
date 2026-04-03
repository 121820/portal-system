package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.CmsTag;
import com.lyx.portal.service.CmsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/api/v1/tags")
public class CmsTagController {

    @Autowired
    private CmsTagService cmsTagService;

    /**
     * 获取标签列表
     */
    @GetMapping
    public IPage<CmsTag> getTags(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return cmsTagService.page(new Page<>(page, size));
    }

    /**
     * 获取标签详情
     */
    @GetMapping("/{id}")
    public CmsTag getTag(@PathVariable Long id) {
        return cmsTagService.getById(id);
    }

    /**
     * 创建标签
     */
    @PostMapping
    public CmsTag createTag(@RequestBody CmsTag tag) {
        cmsTagService.save(tag);
        return tag;
    }

    /**
     * 更新标签
     */
    @PutMapping("/{id}")
    public CmsTag updateTag(@PathVariable Long id, @RequestBody CmsTag tag) {
        tag.setId(id);
        cmsTagService.updateById(tag);
        return tag;
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        cmsTagService.removeById(id);
    }
}