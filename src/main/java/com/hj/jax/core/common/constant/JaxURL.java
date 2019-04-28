package com.hj.jax.core.common.constant;


public class JaxURL {

    /**
     * 公共接口
     */
    public static final String PROVIDE_PROVINCE_DATA = "/public/provideProvinceData.json";
    public static final String PROVIDE_CITY_DATA = "/public/provideCityData.json";
    public static final String PROVIDE_COUNTRY_DATA = "/public/provideCountryData.json";
    public static final String PROVIDE_TRANS_REGION_DATA = "/public/provideTransRegionData.json";
    public static final String PROVIDE_CAR_REGION_DATA = "/public/provideCarRegionData.json";

    /**
     * 系统接口
     */
    public static final String SIVIR_LOGIN = "/api/login.json";
    public static final String SIVIR_LOGOUT = "/api/logout.json";
    public static final String SIVIR_REGISTER = "/api/register.json";
    public static final String PUBLIC_FIND_USER = "/api/public/find/user.json";


    /**
     * 客户接口
     */
    public static final String CUSTOMER_GET_ALL_SORDER = "/api/customer/getAllSorder.json";
    public static final String CUSTOMER_ADD_SORDER = "/api/customer/addSorder.json";
    public static final String CUSTOMER_CANCEL_SORDER = "/api/customer/cancelSorder.json";


    /**
     * 快递员接口
     */
    public static final String TRANS_GET_RECEIVED_SORDER = "/api/trans/getReceivedSorder.json";
    public static final String TRANS_UPDATE_RECEIVED_SORDER_INFO = "/api/trans/updateReceivedSorder.json";
    public static final String TRANS_GET_SEND_SORDER = "/api/trans/getSendSorder.json";
    public static final String TRANS_UPDATE_SEND_SORDER_INFO = "/api/trans/updateSendSorder.json";


    /**
     * 管理员接口 - 用户管理
     */
    public static final String ADMIN_ADD_USER = "/api/admin/addUser.json";
    public static final String ADMIN_DELETE_USER = "/api/admin/deleteUser.json";
    public static final String ADMIN_UPDATE_USER = "/api/admin/updateUser.json";
    public static final String ADMIN_GET_ALL_USER = "/api/admin/getAllUser.json";
    public static final String ADMIN_GET_USER_BY_ID = "/api/admin/getUserById.json";

    /**
     * 管理员接口 - 订单管理
     */
    public static final String ADMIN_DELETE_SORDER = "/api/admin/deleteSorder.json";
    public static final String ADMIN_UPDATE_SORDER = "/api/admin/updateSorder.json";
    public static final String ADMIN_GET_ALL_SORDER = "/api/admin/getAllSorder.json";
    public static final String ADMIN_GET_SORDER_BY_ID = "/api/admin/getSorderById.json";


    /**
     * 管理员接口 - 区域管理 - 配置区域内的快递员和车辆
     */
    public static final String ADMIN_ADD_REGION_INFO = "/api/admin/addRegionInfo.json";
    public static final String ADMIN_DELETE_REGION_INFO = "/api/admin/deleteRegionInfo.json";
    public static final String ADMIN_UPDATE_REGION_INFO = "/api/admin/updateRegionInfo.json";

    public static final String ADMIN_GET_ALL_REGION_INFO = "/api/admin/getAllRegionInfo.json";

    /**
     * 管理员接口 - 配送管理 - 给订单安排运输的快递员和车辆
     */
    public static final String ADMIN_ARRANGE_SORDER_INFO = "/api/admin/arrangeSorderInfo.json";
    public static final String ADMIN_DELETE_SORDER_INFO = "/api/admin/deleteSorderInfo.json";
    public static final String ADMIN_UPDATE_SORDER_INFO = "/api/admin/updateSorderInfo.json";
    public static final String ADMIN_GET_ALL_SORDER_INFO = "/api/admin/getAllSorderInfo.json";

    /**
     * 管理员接口 - 存储管理 - 对存储的车辆进行管理
     */
    public static final String ADMIN_ADD_CAR = "/api/admin/addCar.json";
    public static final String ADMIN_DELETE_CAR = "/api/admin/deleteCar.json";
    public static final String ADMIN_UPDATE_CAR = "/api/admin/updateCar.json";
    public static final String ADMIN_GET_ALL_CAR = "/api/admin/getAllCar.json";

    /**
     * 管理员接口 - 业务统一生成报表
     */
    public static final String ADMIN_GET_BUSINESS_REPORT = "/api/admin/getBusinessReport.json";








}
