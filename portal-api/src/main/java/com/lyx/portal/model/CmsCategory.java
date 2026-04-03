package com.lyx.portal.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 栏目实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cms_category")
public class CmsCategory {
    /**
     * 栏目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 栏目名称
     */
    private String name;

    /**
     * 栏目描述
     */
    private String description;

    /**
     * 父栏目ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（1-正常 0-停用）
     */
    private Integer status;

    /**
     * 创建者
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
}