package com.xuebusi.xbs.user.api;

import com.xuebusi.xbs.user.vo.SysUserVo;

/**
 * 缓存类
 *
 * @Author: syj
 * @CreateDate: 2018/7/29 13:40
 */
public interface IRedisService {

    /**
     * 从缓存根据id查询用户
     *
     * @param id
     * @return
     */
    SysUserVo selectByIdFromCache(Long id);
}
