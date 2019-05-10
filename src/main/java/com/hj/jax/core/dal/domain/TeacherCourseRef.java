package com.hj.jax.core.dal.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hj.jax.core.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teacher_course_ref")
public class TeacherCourseRef extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 引用主键，自增长
     */
    @TableId(value = "teacher_course_ref_id", type = IdType.AUTO)
    private Long teacherCourseRefId;
    /**
     * 用户id
     */
    @TableField("teacher_id")
    private Long teacherId;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Long courseId;
    /**
     * 逻辑删除，0-存在，1-已被删除
     */
    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;

    /**
     *     是否被评分过，0-没有被评分，1-已经被评分
     */
    @TableField("is_marked")
    private Integer isMarked;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("gmt_modified")
    private Date gmtModified;

}
