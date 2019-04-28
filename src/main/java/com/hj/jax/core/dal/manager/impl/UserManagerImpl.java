package com.hj.jax.core.dal.manager.impl;

import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.dal.dao.UserDao;
import com.hj.jax.core.dal.manager.UserManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.stereotype.Component;

@Component
public class UserManagerImpl extends BaseManagerImpl<UserDao, User> implements UserManager{

}
