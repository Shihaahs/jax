package com.hj.jax.core.common.page;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Date;


@Slf4j
public class PageQuery {
    /**
     * 默认所在页码
     */
    public static final Integer DEFAULT_PAGE_CURRENT = 1;
    /**
     * 默认每页条数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 5;

    /**
     * <p> 初始化分页信息 </p>
     *
     * @param pageRequestDTO 传入分页参数
     * @return Page<T>
     */
    public static <T> Page<T> initPage(PageRequestDTO pageRequestDTO) {
        if (null == pageRequestDTO.getPageSize()) {
            pageRequestDTO.setPageSize(DEFAULT_PAGE_SIZE);
        }
        if (null == pageRequestDTO.getPageCurrent()) {
            pageRequestDTO.setPageCurrent(DEFAULT_PAGE_CURRENT);
        }
        return new Page<>(pageRequestDTO.getPageCurrent(), pageRequestDTO.getPageSize());
    }

    /**
     * <p> 进行查询的条件筛选 </p>
     *
     * @param pageRequestDTO
     * @return EntityWrapper<T>
     */
    public static <T> Wrapper<T> conditionAdapter(PageRequestDTO pageRequestDTO) {

        Wrapper wrapper = new EntityWrapper<T>();
        //按照时间进行排序显示
        wrapper.orderDesc(Collections.singleton("gmt_modified"));

        if (null != pageRequestDTO.getUserName() && !pageRequestDTO.getUserName().isEmpty()) {
            wrapper.like("user_name", pageRequestDTO.getUserName().trim());
        }
        if (null != pageRequestDTO.getPhone() && !pageRequestDTO.getPhone().isEmpty()) {
            wrapper.like("phone", pageRequestDTO.getPhone());
        }
        if (null != pageRequestDTO.getPermission()) {
            wrapper.eq("permission", pageRequestDTO.getPermission());
        }

        if (null != pageRequestDTO.getStartTime() && pageRequestDTO.getStartTime().length() != 0) {
            //这里只转换"yyyy-MM-dd"格式的string
            Date starTime = DateUtil.convertToDate(pageRequestDTO.getStartTime(), DateUtil.ZONE_PATTERN);
            if (null != starTime) {
                wrapper.gt("gmt_create", starTime);
            } else {
                log.error(pageRequestDTO.getStartTime() + "转换成" + DateUtil.ZONE_PATTERN + "时出错，不被加入查询条件");
            }
        }
        if (null != pageRequestDTO.getEndTime() && pageRequestDTO.getEndTime().length() != 0) {
            Date endTime = DateUtil.convertToDate(pageRequestDTO.getEndTime(), DateUtil.ZONE_PATTERN);
            if (null != endTime) {
                wrapper.lt("gmt_create", endTime);
            } else {
                log.error(pageRequestDTO.getEndTime() + "转换成" + DateUtil.ZONE_PATTERN + "时出错，不被加入查询条件");
            }
        }

        return wrapper;
    }
}
