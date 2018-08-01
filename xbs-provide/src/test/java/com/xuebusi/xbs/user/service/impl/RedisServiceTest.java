package com.xuebusi.xbs.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuebusi.test.BaseTest;
import com.xuebusi.xbs.user.domain.SysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisServiceTest extends BaseTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void selectByIdFromCache() throws Exception {
        Long id = 79L;
        SysUser sysUser = redisService.selectByIdFromCache(id);
        System.out.println("测试从redis查询用户:" + JSON.toJSONString(sysUser));
    }

}