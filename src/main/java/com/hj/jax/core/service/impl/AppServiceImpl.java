package com.hj.jax.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.hj.jax.core.common.enums.UserPermissionEnum;
import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.vo.CourseVO;
import com.hj.jax.core.common.vo.MarkVO;
import com.hj.jax.core.common.vo.TeacherCourseDTO;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.*;
import com.hj.jax.core.dal.manager.*;
import com.hj.jax.core.service.AppService;
import com.hj.jax.core.service.util.CalculateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hj.jax.core.common.enums.MarkTypeEnum.EXPERT_MARK;
import static com.hj.jax.core.common.enums.MarkTypeEnum.STUDENT_MARK;
import static com.hj.jax.core.common.enums.MarkTypeEnum.TEACHER_MARK;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    CalculateHelper calculateHelper;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private UserCourseRefManager courseRefManager;
    @Autowired
    private TeacherCourseRefManager teacherCourseRefManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private MarkManager markManager;
    @Autowired
    private MarkEngineManager engineManager;


    @Override
    public List<CourseVO> findMark(Long id, Integer type) {
        List<UserCourseRef> courseRefs = courseRefManager.selectList(
                new EntityWrapper<UserCourseRef>()
                        .eq("user_id", id)
                        .eq("is_marked", type));
        List<CourseVO> courseVOList = new ArrayList<>();
        courseRefs.forEach(courseRef -> {
            Long courseId = courseRef.getCourseId();
            Long teacherId = courseRef.getCourseTeacherId();
            Course course = courseManager.selectById(courseId);
            User teacher = userManager.selectById(teacherId);

            CourseVO courseVO = new CourseVO();
            courseVO.setCourseId(courseId);
            courseVO.setTeacherId(teacherId);
            courseVO.setUserId(id);
            courseVO.setCourseName(course.getCourseName());
            courseVO.setTeacherName(teacher.getUserName());
            courseVO.setCourseMark("0");

            if (type == 1) {
                List<Mark> markList = markManager.selectList(new EntityWrapper<Mark>()
                        .eq("mark_user_id", id)
                        .eq("mark_rated_user_id", teacher.getUserId())
                        .eq("mark_course_id", course.getCourseId())
                );
                if (!Objects.isNull(markList) && markList.size() == 1) {
                    courseVO.setCourseMark(markList.get(0).getMarkScore().toString());
                }

            }
            courseVOList.add(courseVO);

        });
        return courseVOList;
    }

    @Override
    public Integer addMarkRecord(Mark mark) {
        //修改课程引用关系
        UserCourseRef courseRef = new UserCourseRef();
        courseRef.setCourseId(mark.getMarkCourseId());
        courseRef.setCourseTeacherId(mark.getMarkRatedUserId());
        courseRef.setUserId(mark.getMarkUserId());
        courseRef = courseRefManager.selectOne(courseRef);
        courseRef.setIsMarked(1);
        int row = courseRefManager.updateById(courseRef);

        if (row == 1) {
            User user = userManager.selectById(mark.getMarkUserId());
            mark.setMarkType(user.getPermission());
            return markManager.insert(mark);
        }
        return 0;
    }

    @Override
    public List<TeacherCourseDTO> getReleaseCourse() {

        List<TeacherCourseRef> teacherCourseRefList = teacherCourseRefManager.selectList(new EntityWrapper<>());
        List<Long> courseIds = Lists.transform(teacherCourseRefList, TeacherCourseRef::getCourseId);
        List<Long> teacherIds = Lists.transform(teacherCourseRefList, TeacherCourseRef::getTeacherId);

        Map<Long, Course> courseMap = courseManager.selectCourseMapByIds(courseIds);
        Map<Long, User> teacherMap = userManager.selectUserMapByUserIds(teacherIds);

        List<TeacherCourseDTO> teacherCourseDTOList = new ArrayList<>();
        teacherCourseRefList.forEach(teacherCourseRef -> {
            TeacherCourseDTO teacherCourseDTO = new TeacherCourseDTO();

            teacherCourseDTO.setCourseId(teacherCourseRef.getCourseId());
            teacherCourseDTO.setCourseName(courseMap.get(teacherCourseRef.getCourseId()).getCourseName());
            teacherCourseDTO.setTeacherId(teacherCourseRef.getTeacherId());
            teacherCourseDTO.setTeacherName(teacherMap.get(teacherCourseRef.getTeacherId()).getUserName());

            teacherCourseDTOList.add(teacherCourseDTO);
        });


        return teacherCourseDTOList;
    }

    @Override
    public List<Course> getAllCourse() {
        return courseManager.selectList(new EntityWrapper<>());
    }

    @Override
    public List<TeacherMarkVO> getTeacherMark(Long teacherId) {
        User teacher = userManager.selectById(teacherId);
        List<TeacherMarkVO> teacherMarkVOList = new ArrayList<>();

            //该老师注册绑定的课程
            List<Long> courseIds = courseRefManager.selectList(
                    new EntityWrapper<UserCourseRef>()
                            .eq("course_teacher_id", teacher.getUserId()))
                    .stream().map(UserCourseRef::getCourseId).collect(Collectors.toList());

            courseIds.forEach( courseId -> {
                TeacherMarkVO teacherMarkVO = new TeacherMarkVO();
                teacherMarkVO.setTeacherId(teacher.getUserId());
                teacherMarkVO.setTeacherName(teacher.getUserName());

                Course course = courseManager.selectById(courseId);
                teacherMarkVO.setCourseName(course.getCourseName());
                //获取评分分
                List<Integer> stuScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, STUDENT_MARK.getCode());
                List<Integer> teaScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, TEACHER_MARK.getCode());
                List<Integer> expScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, EXPERT_MARK.getCode());

                teacherMarkVO.setStuMarkCount(stuScore.size());
                teacherMarkVO.setStuMarkAverage(calculateHelper.calculateArrangeScore((stuScore)));
                teacherMarkVO.setTeaMarkCount(stuScore.size());
                teacherMarkVO.setTeaMarkAverage(calculateHelper.calculateArrangeScore((teaScore)));
                teacherMarkVO.setExpMarkCount(stuScore.size());
                teacherMarkVO.setExpMarkAverage(calculateHelper.calculateArrangeScore((expScore)));

                teacherMarkVO.setFinalAverage(calculateHelper.calculateFinalScore(
                        teacherMarkVO.getStuMarkAverage(),
                        teacherMarkVO.getTeaMarkAverage(),
                        teacherMarkVO.getExpMarkAverage()));

                teacherMarkVOList.add(teacherMarkVO);
            });


        return teacherMarkVOList;
    }



    @Override
    public Integer register(User user) {
        int row = userManager.insert(user);
        if (row == 1) {
            return user.getUserId().intValue();
        }
        return 0;
    }

    @Transactional
    @Override
    public Integer registerCourse(List<UserCourseRef> userCourseRefs) {
        int row;
        for (UserCourseRef courseList : userCourseRefs) {
            row = courseRefManager.insert(courseList);
            if (row != 1) {
                log.error("App端学生专家注册课程失败，course -> {}",courseList.toString());
                return row;
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public Integer registerTeacherCourseRef(List<TeacherCourseRef> teacherCourseRefList) {
        int row;
        for (TeacherCourseRef teacherCourseRef : teacherCourseRefList) {
            row = teacherCourseRefManager.insert(teacherCourseRef);
            if (row != 1) {
                log.error("App端教师上传课程失败，teacherCourseRef -> {}",teacherCourseRef.toString());
                return row;
            }
        }
        return 1;
    }

    @Override
    public User checkLogin(User user) {
        if (null == user.getUserId() || user.getUserId().equals(0L)
                || null == user.getPassword() || user.getPassword().isEmpty()) {
            return null;
        }
        return userManager.selectOne(user);
    }

}
