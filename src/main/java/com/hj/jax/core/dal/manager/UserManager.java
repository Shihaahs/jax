package com.hj.jax.core.dal.manager;

import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.common.base.BaseManager;

import java.util.List;
import java.util.Map;


public interface UserManager extends BaseManager<User> {


    List<User> getUserByUserIds(List<Long> userIds);

    Map<Long,User> selectUserMapByUserIds(List<Long> userIds);

}
