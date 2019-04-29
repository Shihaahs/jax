package com.hj.jax.core.common.vo;


import lombok.Data;

@Data
public class TeacherMarkVO {

    private Long teacherId;

    private String teacherName;

    /**
     * 学生平均分
     */
    private String stuArrangeScore;
    /**
     * 教师平均分
     */
    private String teaArrangeScore;
    /**
     * 专家平均分
     */
    private String expArrangeScore;
    /**
     * 最终得分
     */
    private String finalScore;

}
