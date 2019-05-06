package com.hj.jax.core.common.vo;


import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

@Data
public class MarkVO {

    private Long markId;
    /**
     * 评分人
     */
    private String markUserName;
    /**
     * 被评分人
     */
    private String markRatedUserName;
    /**
     * 评分分数
     */
    private Integer markScore;
    /**
     * 课程
     */
    private String markCourseName;
    /**
     * 评分类型 {@link com.hj.jax.core.common.enums.MarkTypeEnum}
     */
    private String markType;
}
