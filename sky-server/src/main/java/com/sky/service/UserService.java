package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @Auther: szw
 * @Date: 2023/10/29
 * @Description: com.sky.service
 * @version: 1.0
 */
public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
