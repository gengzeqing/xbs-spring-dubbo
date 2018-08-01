package com.xuebusi.xbs.user.mapper;

import com.xuebusi.xbs.annotation.MyBatisRepository;
import com.xuebusi.xbs.user.domain.SysUser;
import com.xuebusi.xbs.user.domain.SysUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface SysUserMapper {
    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    int deleteByPrimaryKey(Long id);

    int save(SysUser record);

    int saveSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    SysUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}