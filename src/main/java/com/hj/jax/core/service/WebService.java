package com.hj.jax.core.service;


import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.vo.MarkVO;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.dal.domain.MarkEngine;
import com.hj.jax.core.dal.domain.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface WebService {

    /**
     * 管理员 - 用户管理
     */
    PageResult<User> getAllUser(PageRequestDTO pageRequestDTO);

    Integer addAdminUser(User user);

    Integer updateAdminUser(User user);

    Integer deleteAdminUser(User user);

    User getAdminUserById(User user);

    /**
     * 管理员 - 评分显示
     */
    PageResult<MarkVO> getAllMark(PageRequestDTO pageRequestDTO);

    //Integer addAdminMark(Mark mark);

    //Integer updateAdminMark(Mark mark);

    Integer deleteAdminMark(Mark mark);

    /**
     * 管理员 - 评分引擎
     */

    PageResult<TeacherMarkVO> getAllTeacherMark(PageRequestDTO pageRequestDTO);

    Integer updateMarkEngine(MarkEngine engine);

    MarkEngine getMarkEngine();

    XSSFWorkbook getMarkReport(PageRequestDTO pageRequestDTO);



}
