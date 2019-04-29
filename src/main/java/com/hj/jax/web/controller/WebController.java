package com.hj.jax.web.controller;

import com.hj.jax.core.common.entity.APIResult;
import com.hj.jax.core.common.enums.GlobalErrorCode;
import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.util.DateUtil;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.dal.domain.MarkEngine;
import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.service.WebService;
import com.hj.jax.web.util.NetWorkUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;

import static com.hj.jax.core.common.constant.JaxURL.*;




@RestController
public class WebController {

    @Autowired
    private WebService webService;

    @RequestMapping(value = ADMIN_GET_ALL_USER, method = RequestMethod.POST)
    public APIResult<PageResult<User>> getAllUser(@RequestBody PageRequestDTO pageRequestDTO) {
        return APIResult.ok(webService.getAllUser(pageRequestDTO));
    }

    @RequestMapping(value = ADMIN_ADD_USER, method = RequestMethod.POST)
    public APIResult<Integer> addUser(@RequestBody User user) {
        return APIResult.ok(webService.addAdminUser(user));
    }

    @RequestMapping(value = ADMIN_DELETE_USER, method = RequestMethod.POST)
    public APIResult<Integer> deleteUser(@RequestBody User user) {
        return APIResult.ok(webService.deleteAdminUser(user));
    }

    @RequestMapping(value = ADMIN_UPDATE_USER, method = RequestMethod.POST)
    public APIResult<Integer> updateUser(@RequestBody User user) {
        return APIResult.ok(webService.updateAdminUser(user));
    }

    @RequestMapping(value = ADMIN_GET_USER_BY_ID, method = RequestMethod.POST)
    public APIResult<User> getUserById(@RequestBody User user) {
        return APIResult.ok(webService.getAdminUserById(user));
    }


    @RequestMapping(value = ADMIN_GET_ALL_MARK, method = RequestMethod.POST)
    public APIResult<PageResult<Mark>> getAllMark(@RequestBody PageRequestDTO pageRequestDTO) {
        return APIResult.ok(webService.getAllMark(pageRequestDTO));
    }

    @RequestMapping(value = ADMIN_DELETE_MARK, method = RequestMethod.POST)
    public APIResult<Integer> deleteAdminMark(@RequestBody Mark mark) {
        return APIResult.ok(webService.deleteAdminMark(mark));
    }

    @RequestMapping(value = ADMIN_MARK_ENGINE, method = RequestMethod.GET)
    public APIResult<MarkEngine> getMarkEngine() {
        return APIResult.ok(webService.getMarkEngine());
    }

    @RequestMapping(value = ADMIN_UPDATE_MARK_ENGINE, method = RequestMethod.POST)
    public APIResult<Integer> updateMarkEngine(@RequestBody MarkEngine engine) {
        return APIResult.ok(webService.updateMarkEngine(engine));
    }

    @RequestMapping(value = ADMIN_GET_TEACHER_MARK, method = RequestMethod.POST)
    public APIResult<PageResult<TeacherMarkVO>> getAllTeacherMark(@RequestBody PageRequestDTO pageRequestDTO) {
        return APIResult.ok(webService.getAllTeacherMark(pageRequestDTO));
    }

    @RequestMapping(value = ADMIN_GET_MARK_REPORT, method = RequestMethod.GET)
    public APIResult<Boolean> getBusinessReport() throws Exception{
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPageSize(1000);
        pageRequestDTO.setPageCurrent(1);

        XSSFWorkbook workbook = webService.getMarkReport(pageRequestDTO);
        if (Objects.isNull(workbook)) {
            APIResult.error(GlobalErrorCode.SYSTEM_EXCEPTION.getCode(), "excel 导出失败");
        }
        HttpServletResponse response = NetWorkUtil.getResonse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + new String("教师评分".getBytes("UTF-8"),"iso-8859-1")
                + DateUtil.parseToString(new Date(),DateUtil.NORMAL_PATTERN) + ".xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        return null;
    }


}
