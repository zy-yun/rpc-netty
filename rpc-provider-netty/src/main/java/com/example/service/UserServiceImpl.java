package com.example.service;

import com.example.IUserService;
import com.example.annotation.ZyRemoteService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yun.zhang
 * @version: v1.0
 * @description:
 * @date:2022/6/1 23:22
 */
@ZyRemoteService
@Slf4j
public class UserServiceImpl implements IUserService {
    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}", name);
        return "save user success!" + name;
    }
}
