package com.xuebusi.xbs.user.controller;

import com.alibaba.fastjson.JSON;
import com.xuebusi.xbs.user.api.IRedisService;
import com.xuebusi.xbs.user.api.ISysUserService;
import com.xuebusi.xbs.user.dto.SysUserDto;
import com.xuebusi.xbs.user.vo.SysUserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统用户控制类
 *
 * @Author: syj
 * @CreateDate: 2018/7/29 13:10
 */
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IRedisService redisService;

    @GetMapping(value = "/testUser")
    public String testUser() {
        return sysUserService.testUser();
    }

    @GetMapping(value = "/countByExample")
    public Integer countByExample(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.countByExample(sysUserDto);
    }

    @GetMapping(value = "/deleteByExample")
    public Boolean deleteByExample(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.deleteByExample(sysUserDto);
    }

    @GetMapping(value = "/deleteById")
    public Boolean deleteById(@RequestParam("id") String id) {
        return sysUserService.deleteById(Long.valueOf(id));
    }

    @GetMapping(value = "/save")
    public Boolean save(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.save(sysUserDto);
    }

    @GetMapping(value = "/saveSelective")
    public Boolean saveSelective(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.saveSelective(sysUserDto);
    }

    @GetMapping(value = "/selectByExample")
    public List<SysUserVo> selectByExample(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.selectByExample(sysUserDto);
    }

    @GetMapping(value = "/getUserById")
    public SysUserVo getUserById(@RequestParam("id") String id) {
        return sysUserService.selectById(Long.valueOf(id));
    }

    @GetMapping(value = "/updateById")
    public Boolean updateById(@RequestBody SysUserDto sysUserDto) {
        return sysUserService.updateById(sysUserDto);
    }

    @GetMapping(value = "/selectByIdFromCache")
    public SysUserVo selectByIdFromCache(@RequestParam("id") String id) {
        SysUserVo sysUserVo = redisService.selectByIdFromCache(Long.valueOf(id));
        System.out.println("根据id从缓存查询用户:" + JSON.toJSONString(sysUserVo));
        return sysUserVo;
    }
}
