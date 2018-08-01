package com.xuebusi.provide.service.impl;

import com.xuebusi.xbs.user.api.IRedisService;
import com.xuebusi.xbs.user.domain.SysUser;
import com.xuebusi.xbs.user.service.impl.RedisService;
import com.xuebusi.xbs.user.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("redisServiceImplRpc")
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisService redisService;

    @Override
    public SysUserVo selectByIdFromCache(Long id) {
        SysUser sysUser = redisService.selectByIdFromCache(id);
        return getSysUserVo(sysUser);
    }

    /**
     * SysUser 转 SysUserVo
     *
     * @param sysUser
     * @return
     */
    private SysUserVo getSysUserVo(SysUser sysUser) {
        if (sysUser != null) {
            SysUserVo sysUserVo = new SysUserVo();
            sysUserVo.setId(String.valueOf(sysUser.getId()));
            sysUserVo.setName(sysUser.getName());
            sysUserVo.setAddress(sysUser.getName());
            sysUserVo.setPhone(sysUser.getPhone());
            sysUserVo.setAge(String.valueOf(sysUser.getAge()));
            return sysUserVo;
        }
        return null;
    }
}
