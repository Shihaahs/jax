package com.hj.jax.core.dal.manager.impl;

import com.hj.jax.core.dal.domain.Course;
import com.hj.jax.core.dal.dao.CourseDao;
import com.hj.jax.core.dal.manager.CourseManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.stereotype.Component;

@Component
public class CourseManagerImpl extends BaseManagerImpl<CourseDao, Course> implements CourseManager{

}
