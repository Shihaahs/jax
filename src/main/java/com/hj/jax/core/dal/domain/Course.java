package com.hj.jax.core.dal.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Course extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 课程id，主键自增长
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Long courseId;
    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;
    /**
     * 逻辑删除，0-存在，1-已被删除
     */
    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;
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
