package com.hj.jax.core.dal.manager.impl;

import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.dal.dao.MarkDao;
import com.hj.jax.core.dal.manager.MarkManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.stereotype.Component;

@Component
public class MarkManagerImpl extends BaseManagerImpl<MarkDao, Mark> implements MarkManager{

}
