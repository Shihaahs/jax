package com.hj.jax.core.common.enums;

import lombok.Getter;

import java.util.Arrays;

public enum UserPermissionEnum {

    ADMIN(0,"超级管理员"),
    STUDENT(1,"学生"),
    TEACHER(2,"教师"),
    EXPERT(3,"专家");


    @Getter
    private Integer code;
    @Getter
    private String desc;

    UserPermissionEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Long code){
        return Arrays.stream(values())
                .filter(e -> e.getCode().equals(code))
                .map(UserPermissionEnum::getDesc)
                .findFirst()
                .orElse("");
    }

}
