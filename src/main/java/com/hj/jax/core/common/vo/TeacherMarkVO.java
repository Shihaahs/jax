package com.hj.jax.core.common.vo;


import lombok.Data;

@Data
public class TeacherMarkVO {

    private Long teacherId;

    private String teacherName;

    private String courseName;

    private Integer stuMarkCount;

    private Integer teaMarkCount;

    private Integer expMarkCount;
    /**
     * 学生平均分
     */
    private String stuMarkAverage;
    /**
     * 教师平均分
     */
    private String teaMarkAverage;
    /**
     * 专家平均分
     */
    private String expMarkAverage;
    /**
     * 最终得分
     */
    private String finalAverage;

}
