package com.xuebusi.provide.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuebusi.test.BaseTest;
import com.xuebusi.xbs.user.domain.SysUser;
import com.xuebusi.xbs.user.domain.SysUserExample;
import com.xuebusi.xbs.user.service.impl.SysUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SysUserServiceImplTest extends BaseTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void testUser() throws Exception {
        System.out.println(sysUserService.testUser());
    }

    @Test
    public void countByExample() throws Exception {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("小李子");
        Integer count = sysUserService.countByExample(example);
        System.out.println("根据条件统计用户数量:" + count);
    }

    @Test
    public void deleteByExample() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void save() throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setName("小李子2");
        sysUser.setAddress("美国2");
        sysUser.setAge(50);
        sysUser.setPhone("234567");
        boolean isOk = sysUserService.save(sysUser);
        System.out.println(isOk == true ? "保存成功" : "保存失败");
    }

    @Test
    public void saveSelective() throws Exception {
        Long id = 79L;
        SysUser sysUser = sysUserService.selectById(id);
        System.out.println("根据id查询用户:" + JSON.toJSONString(sysUser));
        sysUser.setAge(28);
        sysUser.setPhone("88888888");
        sysUser.setAddress("上海");
        Boolean isOk = sysUserService.saveSelective(sysUser);
        System.out.println("根据查询创建新用户:" + isOk);
    }

    @Test
    public void selectByExample() throws Exception {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("小李子");
        List<SysUser> sysUserList = sysUserService.selectByExample(example);
        System.out.println("根据条件查询用户列表:" + JSON.toJSONString(sysUserList));
    }

    @Test
    public void selectById() throws Exception {
        SysUser sysUser = sysUserService.selectById(472945788280799232L);
        System.out.println(JSON.toJSONString(sysUser));
    }

    @Test
    public void updateById() throws Exception {
        Long id = 472961030385762304L;
        SysUser sysUser = sysUserService.selectById(id);
        sysUser.setAge(36);
        Boolean isOk = sysUserService.updateById(sysUser);
        System.out.println("根据id修改用户:" + isOk);
    }

}