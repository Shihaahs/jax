package com.hj.jax.core.common.enums;

import lombok.Getter;

import java.util.Arrays;

public enum MarkTypeEnum {

    STUDENT_MARK(1,"学生评分"),
    TEACHER_MARK(2,"教师评分"),
    EXPERT_MARK(3,"专家评分");


    @Getter
    private Integer code;
    @Getter
    private String desc;

    MarkTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Long code){
        return Arrays.stream(values())
                .filter(e -> e.getCode().equals(code))
                .map(MarkTypeEnum::getDesc)
                .findFirst()
                .orElse("");
    }

}
