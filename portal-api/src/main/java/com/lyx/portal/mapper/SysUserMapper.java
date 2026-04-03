package com.lyx.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyx.portal.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}