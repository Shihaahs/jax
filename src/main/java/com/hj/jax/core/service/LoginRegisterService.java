package com.hj.jax.core.service;


import com.hj.jax.core.dal.domain.User;

public interface LoginRegisterService {

    /**
     * <p> 登录校验，根据手机号和密码->检查数据中是否存在该用户 </p>
     * @param user
     * @return User
     *
     */
    User checkLogin(User user);

    /**
     * <p> 注册用户 </p>
     * @param user
     * @return Integer 返回注册结果
     *
     */
    Integer registerUser(User user);

    /**
     * <p> 注册校验，根据手机号去查是否有已经存在的用户 </p>
     * @param phone
     * @return boolean
     *
     */
    boolean checkRegister(String phone);
}
