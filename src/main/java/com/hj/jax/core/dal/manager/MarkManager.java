package com.hj.jax.core.dal.manager;

import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.common.base.BaseManager;

import java.util.List;


public interface MarkManager extends BaseManager<Mark> {

    /**
     * <p> 获取相应教师的系列评分 </p>
     * @param teacherId 教师id
     * @param courseId 该教师注册绑定的课程id
     * @param markType 分数类型
     * @return List<Integer>
     * @author huangji
     * @date 2019/4/29 11:02
     * @since V1.0.0-SNAPSHOT
     *
     */
    List<Integer> getMarkByTeacherId(Long teacherId,Long courseId, Integer markType);
}
