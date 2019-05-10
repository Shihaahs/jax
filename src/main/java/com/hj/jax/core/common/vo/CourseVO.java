package com.hj.jax.core.common.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CourseVO implements Serializable {

    private String teacherName;

    private String courseName;

    private String courseMark;


    private Long teacherId;
    private Long courseId;
    private Long userId;

}
