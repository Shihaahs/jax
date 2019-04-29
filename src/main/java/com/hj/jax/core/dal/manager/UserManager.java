package com.hj.jax.core.dal.manager;

import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.common.base.BaseManager;

import java.util.List;


public interface UserManager extends BaseManager<User> {


    List<User> getUserByUserIds(List<Long> userIds);

}
