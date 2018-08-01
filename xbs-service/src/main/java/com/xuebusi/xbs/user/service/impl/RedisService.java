package com.xuebusi.xbs.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuebusi.xbs.cache.support.RedisOpsString;
import com.xuebusi.xbs.user.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class RedisService {

    private static final String XBS_SPRING_DUBBO_SYS_USER = "XBS-SPRING-DUBBO-SYS-USER-ID:{0}";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisOpsString redisOpsString;

    public SysUser selectByIdFromCache(Long id) {
        String key = MessageFormat.format(XBS_SPRING_DUBBO_SYS_USER, id);
        String sysUserJson = redisOpsString.get(key);
        System.out.println("从redis根据id查询用户:" + sysUserJson);
        if (StringUtils.isNotBlank(sysUserJson)) {
            SysUser sysUser = JSON.parseObject(sysUserJson, SysUser.class);
            return sysUser;
        }
        SysUser sysUser = sysUserService.selectById(id);
        if (sysUser != null) {
            redisOpsString.set(key, JSON.toJSONString(sysUser));
            System.out.println("缓存用户信息到redis:" + JSON.toJSONString(sysUser));
            return sysUser;
        }
        return null;
    }

}
