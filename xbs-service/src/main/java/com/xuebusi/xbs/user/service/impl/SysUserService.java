package com.xuebusi.xbs.user.service.impl;

import com.xuebusi.xbs.user.domain.SysUser;
import com.xuebusi.xbs.user.domain.SysUserExample;
import com.xuebusi.xbs.user.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public String testUser() {
        return "====测试OK====";
    }

    public Integer countByExample(SysUserExample example) {
        return sysUserMapper.countByExample(example);
    }

    public Boolean deleteByExample(SysUserExample example) {
        return sysUserMapper.deleteByExample(example) > 0;
    }

    public Boolean deleteById(Long id) {
        return sysUserMapper.deleteByPrimaryKey(id) > 0;
    }

    public Boolean save(SysUser sysUser) {
        return sysUserMapper.save(sysUser) > 0;
    }

    public Boolean saveSelective(SysUser sysUser) {
        return sysUserMapper.saveSelective(sysUser) > 0;
    }

    public List<SysUser> selectByExample(SysUserExample example) {
        return sysUserMapper.selectByExample(example);
    }

    public SysUser selectById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public Boolean updateById(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKey(sysUser) > 0;
    }

}
