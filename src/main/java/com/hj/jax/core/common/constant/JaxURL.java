package com.hj.jax.core.common.constant;


public class JaxURL {

    /**
     * 公共接口
     */

    /**
     * 系统接口
     */
    public static final String SIVIR_LOGIN = "/api/login.json";
    public static final String SIVIR_LOGOUT = "/api/logout.json";
    public static final String SIVIR_REGISTER = "/api/register.json";
    public static final String PUBLIC_FIND_USER = "/api/public/find/user.json";


    /**
     * 管理员接口 - 用户管理
     */
    public static final String ADMIN_ADD_USER = "/api/admin/addUser.json";
    public static final String ADMIN_DELETE_USER = "/api/admin/deleteUser.json";
    public static final String ADMIN_UPDATE_USER = "/api/admin/updateUser.json";
    public static final String ADMIN_GET_ALL_USER = "/api/admin/getAllUser.json";
    public static final String ADMIN_GET_USER_BY_ID = "/api/admin/getUserById.json";

    /**
     * 管理员接口 - 评分显示
     */
    public static final String ADMIN_DELETE_MARK = "/api/admin/deleteMark.json";
    public static final String ADMIN_GET_ALL_MARK = "/api/admin/getAllMark.json";


    /**
     * 管理员接口 - 评分引擎
     */
    public static final String ADMIN_MARK_ENGINE = "/api/admin/getMarkEngine.json";
    public static final String ADMIN_UPDATE_MARK_ENGINE = "/api/admin/updateMarkEngine.json";
    public static final String ADMIN_GET_TEACHER_MARK = "/api/admin/getAllTeacherMark.json";

    /**
     * 管理员接口 - 评分统一生成报表
     */
    public static final String ADMIN_GET_MARK_REPORT = "/api/admin/getMarkReport.json";








}
