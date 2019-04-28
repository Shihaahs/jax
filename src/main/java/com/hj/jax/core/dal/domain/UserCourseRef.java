package com.hj.jax.core.dal.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.hj.jax.core.common.base.BaseModel;
import com.baomidou.mybatisplus.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_course_ref")
public class UserCourseRef extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 引用主键，自增长
     */
    @TableId(value = "user_course_ref_id", type = IdType.AUTO)
    private Long userCourseRefId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 课程id
     */
    @TableField("course_id")
    private Long courseId;
    /**
     * 逻辑删除，0-存在，1-已被删除
     */
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;

}
