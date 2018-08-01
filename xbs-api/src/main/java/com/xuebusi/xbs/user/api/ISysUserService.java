package com.xuebusi.xbs.user.api;

import com.xuebusi.xbs.user.dto.SysUserDto;
import com.xuebusi.xbs.user.vo.SysUserVo;

import java.util.List;

/**
 * 系统用户服务接口
 *
 * @Author: syj
 * @CreateDate: 2018/7/25 16:46
 */
public interface ISysUserService {

    String testUser();

    Integer countByExample(SysUserDto sysUserDto);

    Boolean deleteByExample(SysUserDto sysUserDto);

    Boolean deleteById(Long id);

    Boolean save(SysUserDto sysUserDto);

    Boolean saveSelective(SysUserDto sysUserDto);

    List<SysUserVo> selectByExample(SysUserDto sysUserDto);

    SysUserVo selectById(Long id);

    Boolean updateById(SysUserDto sysUserDto);
}
