package com.hj.jax.core.service;

import com.hj.jax.core.common.vo.CourseVO;
import com.hj.jax.core.common.vo.MarkVO;
import com.hj.jax.core.common.vo.TeacherCourseDTO;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.*;

import java.util.List;

public interface AppService {



    List<CourseVO> findMark(Long id, Integer type);

    Integer addMarkRecord(Mark mark);

    List<TeacherMarkVO> getTeacherMark(Long teacherId);

    List<TeacherCourseDTO> getReleaseCourse();

    List<Course> getAllCourse();

    /**
     * <p> 登录校验，根据手机号和密码->检查数据中是否存在该用户 </p>
     * @param user
     * @return User
     *
     */
    User checkLogin(User user);

    Integer register(User user);

    Integer registerCourse(List<UserCourseRef> userCourseRefList);

    Integer registerTeacherCourseRef(List<TeacherCourseRef> teacherCourseRefList);



}
