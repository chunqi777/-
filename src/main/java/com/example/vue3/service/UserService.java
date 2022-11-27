package com.example.vue3.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.vue3.common.Result;
import com.example.vue3.entity.User;
import com.example.vue3.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

//用户数据查询
@Service
public class UserService {

    //获取mybatis_plus操作类
    @Resource
    private UserMapper userMapper;

    //用户注册
    public Result<?> insert(User user) {
        //设置用户唯一标识
        user.setUuid(IdUtil.fastSimpleUUID());
        //数据新增
        userMapper.insert(user);
        //返回成功标识
        return Result.success();
    }

    //分页查询
    public Result<?> findPage(Integer pageNum, Integer pageSize, String search) {
        //根据页数，页码，搜索项进行分页查询
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<User>lambdaQuery().like(User::getNickName, search));
        //返回查询结果
        return Result.success(userPage);
    }

    //用户数据变更
    public Result<?> updata(User user) {
        //通过id进行变更
        userMapper.updateById(user);
        return Result.success();
    }

    //用户删除
    public Result<?> delete(Long id) {
        //通过id删除
        userMapper.deleteById(id);
        return Result.success();
    }

    //用户登陆
    public Result<?> login(User user, HttpServletResponse response) {
        //校对用户登陆信息
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));

        //数据库未查询到则判断用户不存在
        if (res == null) {
            return Result.error("-1", "用户名或密码错误！");
        }

        //设置cookie
        Cookie cookie = new Cookie("id", res.getId().toString());

        cookie.setPath("/");

        response.addCookie(cookie);
        return Result.success();
    }

    //通过id查询登陆状态
    public Result<?> selectOne(User user) {
        if (user.getId() == null) {
            return Result.error("-1", "未登录");
        }
        User one = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        return Result.success(one);
    }
}
