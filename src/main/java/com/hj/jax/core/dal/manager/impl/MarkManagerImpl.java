package com.hj.jax.core.dal.manager.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.dal.dao.MarkDao;
import com.hj.jax.core.dal.manager.MarkManager;
import com.hj.jax.core.common.base.BaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkManagerImpl extends BaseManagerImpl<MarkDao, Mark> implements MarkManager{

    @Autowired
    private MarkDao markDao;

    @Override
    public List<Integer> getMarkByTeacherId(Long teacherId, Long courseId, Integer markType) {
        List<Mark> markList = markDao.selectList(new EntityWrapper<Mark>()
                .eq("mark_rated_user_id", teacherId)
                .eq("mark_course_id", courseId)
                .eq("mark_type",markType));

        return Lists.transform(markList, Mark::getMarkScore);
    }

}
