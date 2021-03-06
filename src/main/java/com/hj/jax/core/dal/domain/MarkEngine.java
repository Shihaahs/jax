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
@TableName("mark_engine")
public class MarkEngine extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 主键自增长
     */
    @TableId(value = "mark_engine_id", type = IdType.AUTO)
    private Long markEngineId;
    /**
     * 学生评分权重，百分比
     */
    @TableField("student_weight")
    private Integer studentWeight;
    /**
     * 教师评分权重，百分比
     */
    @TableField("teacher_weight")
    private Integer teacherWeight;
    /**
     * 专家评分权重，百分比
     */
    @TableField("expert_weight")
    private Integer expertWeight;
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
