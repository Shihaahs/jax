package com.hj.jax.core.common.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherCourseDTO implements Serializable {

    private String teacherName;

    private String courseName;

    private Long teacherId;

    private Long courseId;

}
