package com.company.pythonlearning.service;

import com.company.pythonlearning.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     * @throws Exception 登录失败时抛出异常
     */
    User login(String username, String password) throws Exception;

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 用户信息
     * @throws Exception 注册失败时抛出异常
     */
    User register(String username, String password, String email) throws Exception;

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(String email);
}