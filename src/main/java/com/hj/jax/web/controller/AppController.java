package com.hj.jax.web.controller;

import com.hj.jax.core.common.entity.APIResult;
import com.hj.jax.core.common.enums.GlobalErrorCode;
import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.util.DateUtil;
import com.hj.jax.core.common.vo.CourseVO;
import com.hj.jax.core.common.vo.MarkVO;
import com.hj.jax.core.common.vo.TeacherCourseDTO;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.*;
import com.hj.jax.core.dal.manager.UserManager;
import com.hj.jax.core.service.AppService;
import com.hj.jax.core.service.LoginRegisterService;
import com.hj.jax.core.service.WebService;
import com.hj.jax.web.util.NetWorkUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.hj.jax.core.common.constant.JaxURL.*;
import static com.hj.jax.core.common.enums.GlobalErrorCode.LOGIN_FAILURE;

@Slf4j
@RestController
public class AppController {

    @Autowired
    private WebService webService;
    @Autowired
    private AppService appService;
    @Autowired
    private UserManager userManager;

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = JAX_LOGIN, method = RequestMethod.POST)
    public APIResult<User> getAllUser(@RequestParam String userId,@RequestParam String password) {
        User user = new User();
        user.setUserId(Long.valueOf(userId));
        user.setPassword(password);
        user = appService.checkLogin(user);
        if (null != user) {
            if (user.getUserName().isEmpty()) {
                return APIResult.error(LOGIN_FAILURE.getCode(), LOGIN_FAILURE.getMessage());
            }
            log.info("login -> " + user.getUserName() + "用户已登录 ");
            return APIResult.ok(user);
        }
        return APIResult.error(LOGIN_FAILURE.getCode(), LOGIN_FAILURE.getMessage());
    }

    @RequestMapping(value = "/api/getPreMarkById.json", method = RequestMethod.POST)
    public APIResult<List<CourseVO>> getPreMarkById(@RequestParam Long userId) {
        return APIResult.ok(appService.findMark(userId, 0));
    }

    @RequestMapping(value = "/api/getDoneMarkById.json", method = RequestMethod.POST)
    public APIResult<List<CourseVO>> getDoneMarkById(@RequestParam Long userId) {
        return APIResult.ok(appService.findMark(userId, 1));
    }


    @RequestMapping(value = "/api/doMark.json", method = RequestMethod.POST)
    public APIResult<Integer> doMark(@RequestParam Long userId,@RequestParam Long teacherId,
                              @RequestParam Long courseId,@RequestParam Integer courseMark) {
        Mark mark = new Mark();
        mark.setMarkCourseId(courseId);
        mark.setMarkRatedUserId(teacherId);
        mark.setMarkUserId(userId);
        mark.setMarkScore(courseMark);
        return APIResult.ok(appService.addMarkRecord(mark));
    }
    @RequestMapping(value = "/api/getTeacherMark.json", method = RequestMethod.POST)
    public APIResult<List<TeacherMarkVO>> getTeacherMark(@RequestParam Long userId) {
        return APIResult.ok(appService.getTeacherMark(userId));
    }

    @RequestMapping(value = "/api/getReleaseCourse.json", method = RequestMethod.POST)
    public APIResult<List<TeacherCourseDTO>> getReleaseCourse() {
        return APIResult.ok(appService.getReleaseCourse());
    }



    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = JAX_REGISTER, method = RequestMethod.POST)
    public APIResult<Integer> register(@RequestParam String userName,@RequestParam String password,@RequestParam Integer permission) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setPermission(permission);
        return APIResult.ok(appService.register(user));
    }

    @RequestMapping(value = JAX_REGISTER_COURSE, method = RequestMethod.POST)
    public APIResult<Integer> registerCourse(@RequestBody List<UserCourseRef> userCourseRefList) {
        return APIResult.ok(appService.registerCourse(userCourseRefList));
    }

    @RequestMapping(value = JAX_REGISTER_TEACHER_COURSE_REF, method = RequestMethod.POST)
    public APIResult<Integer> registerTeacherCourseRef(@RequestBody List<TeacherCourseRef> teacherCourseRefs) {
        return APIResult.ok(appService.registerTeacherCourseRef(teacherCourseRefs));
    }

    @RequestMapping(value = JAX_GET_ALL_COURSE, method = RequestMethod.POST)
    public APIResult<List<Course>> getAllCourse() {
        return APIResult.ok(appService.getAllCourse());
    }




}
