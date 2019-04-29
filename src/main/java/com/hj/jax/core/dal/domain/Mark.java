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
public class Mark extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 评分id，主键，自增长
     */
    @TableId(value = "mark_id", type = IdType.AUTO)
    private Long markId;
    /**
     * 评分人id
     */
    @TableField("mark_user_id")
    private Long markUserId;
    /**
     * 被评分人id
     */
    @TableField("mark_rated_user_id")
    private Long markRatedUserId;
    /**
     * 评分分数
     */
    @TableField("mark_score")
    private Integer markScore;
    /**
     * 课程id
     */
    @TableField("mark_course_id")
    private Long markCourseId;

    /**
     * 评分分数
     */
    @TableField("mark_type")
    private Integer markType;
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
