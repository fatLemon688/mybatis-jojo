package org.jojo.mybatis.demo.service;

import org.jojo.mybatis.demo.entity.User;

/**
 *  用户Service
 */
public interface UserService {
    User findOne(Integer id);
}
