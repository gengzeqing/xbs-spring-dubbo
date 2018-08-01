package com.xuebusi.provide.service.impl;

import com.xuebusi.xbs.user.api.ISysUserService;
import com.xuebusi.xbs.user.domain.SysUser;
import com.xuebusi.xbs.user.domain.SysUserExample;
import com.xuebusi.xbs.user.dto.SysUserDto;
import com.xuebusi.xbs.user.service.impl.SysUserService;
import com.xuebusi.xbs.user.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户服务实现类
 *
 * @Author: syj
 * @CreateDate: 2018/7/25 16:39
 */
@Service("sysUserServiceImplRpc")
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserService sysUserService;


    @Override
    public String testUser() {
        return "====测试OK====";
    }

    /**
     * 根据条件统计用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Integer countByExample(SysUserDto sysUserDto) {
        SysUserExample example = getSysUserExample(sysUserDto);
        return sysUserService.countByExample(example);
    }

    /**
     * 根据条件删除用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Boolean deleteByExample(SysUserDto sysUserDto) {
        SysUserExample example = getSysUserExample(sysUserDto);
        return sysUserService.deleteByExample(example);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(Long id) {
        return sysUserService.deleteById(id);
    }

    /**
     * 添加用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Boolean save(SysUserDto sysUserDto) {
        SysUser sysUser = getSysUser(sysUserDto);
        return sysUserService.save(sysUser);
    }

    @Override
    public Boolean saveSelective(SysUserDto sysUserDto) {
        return null;
    }

    /**
     * 根据条件查询用户
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public List<SysUserVo> selectByExample(SysUserDto sysUserDto) {
        SysUserExample example = getSysUserExample(sysUserDto);
        List<SysUser> sysUserList = sysUserService.selectByExample(example);
        List<SysUserVo> sysUserVoList = getSysUserVoList(sysUserList);
        return sysUserVoList;
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public SysUserVo selectById(Long id) {
        SysUser sysUser = sysUserService.selectById(id);
        return getSysUserVo(sysUser);
    }

    /**
     * 根据id更新用户信息
     *
     * @param sysUserDto
     * @return
     */
    @Override
    public Boolean updateById(SysUserDto sysUserDto) {
        SysUser sysUser = getSysUser(sysUserDto);
        return sysUserService.updateById(sysUser);
    }

    /**
     * 设置用户条件
     *
     * @param sysUserDto
     * @return
     */
    private SysUserExample getSysUserExample(SysUserDto sysUserDto) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysUserDto.getName())) {
            criteria.andNameEqualTo(sysUserDto.getName());
        }

        if (StringUtils.isNotBlank(sysUserDto.getAge())) {
            criteria.andAgeEqualTo(Integer.valueOf(sysUserDto.getAge()));
        }

        if (StringUtils.isNotBlank(sysUserDto.getAddress())) {
            criteria.andAddressEqualTo(sysUserDto.getAddress());
        }

        if (StringUtils.isNotBlank(sysUserDto.getPhone())) {
            criteria.andPhoneEqualTo(sysUserDto.getPhone());
        }
        return example;
    }

    /**
     * SysUserDto 转 SysUser
     *
     * @param sysUserDto
     * @return
     */
    private SysUser getSysUser(SysUserDto sysUserDto) {
        SysUser sysUser = new SysUser();
        sysUser.setName(sysUserDto.getName());
        sysUser.setAddress(sysUserDto.getAddress());
        sysUser.setPhone(sysUserDto.getPhone());
        sysUser.setAge(Integer.valueOf(sysUserDto.getAge()));
        return sysUser;
    }

    /**
     * SysUser 集合转成 SysUserVo 集合
     *
     * @param sysUserList
     * @return
     */
    private List<SysUserVo> getSysUserVoList(List<SysUser> sysUserList) {
        if (sysUserList != null && sysUserList.size() > 0) {
            List<SysUserVo> sysUserVoList = new ArrayList<>();
            for (SysUser sysUser : sysUserList) {
                SysUserVo sysUserVo = getSysUserVo(sysUser);
                sysUserVoList.add(sysUserVo);
            }
            return sysUserVoList;
        }
        return null;
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
