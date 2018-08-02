package com.xuebusi.xbs.user.controller;

import com.alibaba.fastjson.JSON;
import com.xuebusi.xbs.user.api.IRedisService;
import com.xuebusi.xbs.user.api.ISysUserService;
import com.xuebusi.xbs.user.dto.SysUserDto;
import com.xuebusi.xbs.user.vo.SysUserVo;
import com.xuebusi.xbs.util.logutils.LogMgr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "测试登录 (耿泽庆)")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IRedisService redisService;

    @ApiOperation(value = "测试接口是否联通", notes = "测试接口是否联通", httpMethod = "GET")
    @GetMapping(value = "/testUser")
    public String testUser() {
        String user = null;
        try {
            user = sysUserService.testUser();
            LogMgr.sysInfo("出参：" + user);
        } catch (Exception e) {
            LogMgr.error("错误：" + e.toString());
        }
        return user;
    }

    @ApiOperation(value = "统计信息", notes = "统计信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserDto", value = "\t{\n" +
                    "\t	\"id\":\"ID\",\n" +
                    "\t	\"name\":\"名称\",\n" +
                    "\t	\"address\":\"地址\",\n" +
                    "\t	\"age\":\"年龄\",\n" +
                    "\t	\"phone\":\"电话\",\n" +
                    "\t}\n"
                    ,required = true, dataType = "String_JSON")
    })
    @PostMapping(value = "/countByExample")
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
