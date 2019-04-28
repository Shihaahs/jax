package com.hj.jax.core.common.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class PageRequestDTO implements Serializable {

    /**
     * 用户名
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户角色
     */
    private String permission;
    /**
     * 区域id
     */
    private Long regionId;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     *当前页码
     */
    @NotNull
    private Integer pageCurrent;
    /**
     *当前页上的记录数
     */
    @NotNull
    private Integer pageSize;
    /**
     *总记录数
     */
    private Integer pageCount;

}
