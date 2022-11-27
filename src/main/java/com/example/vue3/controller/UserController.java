package com.example.vue3.controller;


import com.example.vue3.common.Result;
import com.example.vue3.entity.User;
import com.example.vue3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


//用户数据查询接口
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public Result<?> login(@RequestBody User user, HttpServletResponse response) {
        return userService.login(user, response);
    }

    @GetMapping("/cookie")
    public Result<?> selectById(@CookieValue(value = "id") String id) {
        User user = new User();
        user.setId(Integer.parseInt(id));
        return userService.selectOne(user);
    }


    @PostMapping
    public Result<?> save(@RequestBody User user) {
        return userService.insert(user);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        return userService.findPage(pageNum, pageSize, search);
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        return userService.updata(user);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
