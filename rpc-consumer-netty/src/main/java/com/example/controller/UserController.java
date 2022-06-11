package com.example.controller;

import com.example.IUserService;
import com.example.annotation.ZyRemoteReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/8 17:49
 */
@RestController
public class UserController {

    @ZyRemoteReference
    private IUserService userService;

    @RequestMapping("/save")
    public String saveUser(){
        return userService.saveUser("zy");
    }
}
