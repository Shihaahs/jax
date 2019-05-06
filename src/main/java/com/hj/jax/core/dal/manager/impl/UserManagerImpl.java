package com.hj.jax.core.dal.manager.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.dal.dao.UserDao;
import com.hj.jax.core.dal.manager.UserManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class UserManagerImpl extends BaseManagerImpl<UserDao, User> implements UserManager{

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserByUserIds(List<Long> userIds) {

        return userDao.selectList(new EntityWrapper<User>().in("user_id", userIds));
    }

    @Override
    public Map<Long, User> selectUserMapByUserIds(List<Long> userIds) {
        List<User> list = userDao.selectList(new EntityWrapper<User>().in("user_id", userIds));
        return list.stream().collect(Collectors.toMap(User::getUserId, x -> x));
    }
}
