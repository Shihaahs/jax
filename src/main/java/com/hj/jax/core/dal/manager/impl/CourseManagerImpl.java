package com.hj.jax.core.dal.manager.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.hj.jax.core.dal.domain.Course;
import com.hj.jax.core.dal.dao.CourseDao;
import com.hj.jax.core.dal.manager.CourseManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CourseManagerImpl extends BaseManagerImpl<CourseDao, Course> implements CourseManager{

    @Autowired
    private CourseDao courseDao;

    @Override
    public Map<Long, Course> selectCourseMapByIds(List<Long> courseIds) {
        List<Course> list  = courseDao.selectList(new EntityWrapper<Course>().in("course_id", courseIds));
        return list.stream().collect(Collectors.toMap(Course::getCourseId, x -> x));
    }
}
