package com.hj.jax.core.dal.manager;

import com.hj.jax.core.dal.domain.Course;
import com.hj.jax.core.common.base.BaseManager;

import java.util.List;
import java.util.Map;


public interface CourseManager extends BaseManager<Course> {


    Map<Long,Course> selectCourseMapByIds(List<Long> courseIds);

}
