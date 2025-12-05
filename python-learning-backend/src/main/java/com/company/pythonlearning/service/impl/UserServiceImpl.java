package com.company.pythonlearning.service.impl;

import com.company.pythonlearning.entity.User;
import com.company.pythonlearning.repository.UserRepository;
import com.company.pythonlearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new Exception("用户名不存在");
        }

        User user = optionalUser.get();
        if (!user.getEnabled()) {
            throw new Exception("用户已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("密码错误");
        }

        // 更新最后登录时间
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User register(String username, String password, String email) throws Exception {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new Exception("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(email)) {
            throw new Exception("邮箱已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setNickname(username); // 默认昵称与用户名相同
        user.setAvatar("https://via.placeholder.com/100"); // 默认头像
        user.setLevel(1);
        user.setExperience(0);
        user.setTotalLearningTime(0);
        user.setCompletedModules(0);
        user.setRole("USER");
        user.setEnabled(true);
        user.setJoinDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }
}