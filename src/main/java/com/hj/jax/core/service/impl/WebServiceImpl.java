package com.hj.jax.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.hj.jax.core.common.enums.MarkTypeEnum;
import com.hj.jax.core.common.enums.UserPermissionEnum;
import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.util.DateUtil;
import com.hj.jax.core.common.vo.MarkVO;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.*;
import com.hj.jax.core.dal.manager.*;
import com.hj.jax.core.service.WebService;
import com.hj.jax.core.service.util.CalculateHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.hj.jax.core.common.enums.MarkTypeEnum.*;
import static com.hj.jax.core.common.page.PageQuery.conditionAdapter;
import static com.hj.jax.core.common.page.PageQuery.initPage;

@Slf4j
@Service
public class WebServiceImpl implements WebService {

    private static final Integer COLUMN_WIDTH = 25 * 256;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00"); //平均分保留两位小数点

    @Autowired
    CalculateHelper calculateHelper;
    @Autowired
    private CourseManager courseManager;
    @Autowired
    private UserCourseRefManager courseRefManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private MarkManager markManager;
    @Autowired
    private MarkEngineManager engineManager;
    @Autowired
    private TeacherCourseRefManager teacherCourseRefManager;



    @Override
    public PageResult<User> getAllUser(PageRequestDTO pageRequestDTO) {

        //分页条件查询
        Page<User> userPage = userManager.selectPage(
                initPage(pageRequestDTO),
                conditionAdapter(pageRequestDTO));

        return new PageResult<>(pageRequestDTO.getPageSize(),
                pageRequestDTO.getPageCurrent(),
                (int) userPage.getTotal(),
                userPage.getRecords());
    }

    /**
     * 暂时用不到
     */
    @Override
    public Integer addAdminUser(User user) {
        return null;
    }

    @Override
    public Integer updateAdminUser(User user) {
        if (Objects.isNull(user.getUserId()) || user.getUserId().equals(0L)) {
            log.error("WebServiceImpl - updateAdminUser -> 用户 id 为空");
            return 0;
        }

        return userManager.updateById(user);
    }

    @Override
    public Integer deleteAdminUser(User user) {
        if (Objects.isNull(user.getUserId()) || user.getUserId().equals(0L)) {
            log.error("WebServiceImpl - deleteAdminUser -> 用户 id 为空");
            return 0;
        }
        return userManager.deleteById(user.getUserId());
    }

    @Override
    public User getAdminUserById(User user) {
        if (Objects.isNull(user.getUserId()) || user.getUserId().equals(0L)) {
            log.error("WebServiceImpl - getAdminUserById -> 用户 id 为空");
            return new User();
        }
        return userManager.selectOne(user);
    }


    @Override
    public PageResult<MarkVO> getAllMark(PageRequestDTO pageRequestDTO) {
        //分页条件查询
        Page<Mark> markPage = markManager.selectPage(
                initPage(pageRequestDTO),
                conditionAdapter(pageRequestDTO));

        List<Long> markUserIds = Lists.transform(markPage.getRecords(), Mark::getMarkUserId);
        List<Long> markRatedUserIds = Lists.transform(markPage.getRecords(), Mark::getMarkRatedUserId);
        List<Long> courseIds = Lists.transform(markPage.getRecords(), Mark::getMarkCourseId);

        Map<Long, User> markUserMap = userManager.selectUserMapByUserIds(markUserIds);
        Map<Long, User> markRatedUserMap = userManager.selectUserMapByUserIds(markRatedUserIds);
        Map<Long, Course> courseMap = courseManager.selectCourseMapByIds(courseIds);

        List<MarkVO> markVOList = new ArrayList<>();

        markPage.getRecords().forEach(mark -> {
            MarkVO markVO = new MarkVO();
            Long markId = mark.getMarkId();
            markVO.setMarkId(markId);
            markVO.setMarkUserName(markUserMap.get(mark.getMarkUserId()).getUserName());
            markVO.setMarkRatedUserName(markRatedUserMap.get(mark.getMarkRatedUserId()).getUserName());
            markVO.setMarkCourseName(courseMap.get(mark.getMarkCourseId()).getCourseName());
            markVO.setMarkType(MarkTypeEnum.getDesc(mark.getMarkType()));
            markVO.setMarkScore(mark.getMarkScore());
            markVOList.add(markVO);
        });

        return new PageResult<>(pageRequestDTO.getPageSize(),
                pageRequestDTO.getPageCurrent(),
                (int) markPage.getTotal(),
                markVOList);
    }

    @Override
    public Integer deleteAdminMark(Mark mark) {
        if (Objects.isNull(mark.getMarkId()) || mark.getMarkId().equals(0L)) {
            log.error("WebServiceImpl - deleteAdminMark -> 评分 id 为空");
            return 0;
        }
        return markManager.deleteById(mark.getMarkId());
    }

    @Override
    public Integer updateMarkEngine(MarkEngine engine) {
        if (null == engine.getStudentWeight()) {
           log.error("修改评分引擎失败 -> 学生权重为空");
           return 0;
        }
        if (null == engine.getTeacherWeight()) {
            log.error("修改评分引擎失败 -> 教师权重为空");
            return 0;
        }
        if (null == engine.getExpertWeight()) {
            log.error("修改评分引擎失败 -> 专家权重为空");
            return 0;
        }
        engine.setMarkEngineId(1L);
        return engineManager.updateById(engine);
    }

    @Override
    public MarkEngine getMarkEngine() {
        return engineManager.getMarkEngine();
    }

    @Override
    public PageResult<TeacherMarkVO> getAllTeacherMark(PageRequestDTO pageRequestDTO) {
        List<TeacherMarkVO> teacherMarkVOList = new ArrayList<>();

        List<Long> teacherIds = Lists.transform(userManager.selectList(new EntityWrapper<User>()
                .eq("permission", UserPermissionEnum.TEACHER.getCode())), User::getUserId);

        userManager.getUserByUserIds(teacherIds).forEach(teacher -> {

            //该老师注册绑定的课程
            List<Long> courseIds = teacherCourseRefManager.selectList(
                    new EntityWrapper<TeacherCourseRef>()
                            .eq("teacher_id", teacher.getUserId()))
                    .stream().map(TeacherCourseRef::getCourseId).collect(Collectors.toList());

            courseIds.forEach( courseId -> {
                TeacherMarkVO teacherMarkVO = new TeacherMarkVO();
                teacherMarkVO.setTeacherId(teacher.getUserId());
                teacherMarkVO.setTeacherName(teacher.getUserName());

                Course course = courseManager.selectById(courseId);
                teacherMarkVO.setCourseName(course.getCourseName());
                //获取评分分
                List<Integer> stuScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, STUDENT_MARK.getCode());
                List<Integer> teaScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, TEACHER_MARK.getCode());
                List<Integer> expScore = markManager.getMarkByTeacherId(teacher.getUserId(),courseId, EXPERT_MARK.getCode());

                teacherMarkVO.setStuMarkCount(stuScore.size());
                teacherMarkVO.setStuMarkAverage(calculateHelper.calculateArrangeScore((stuScore)));
                teacherMarkVO.setTeaMarkCount(stuScore.size());
                teacherMarkVO.setTeaMarkAverage(calculateHelper.calculateArrangeScore((teaScore)));
                teacherMarkVO.setExpMarkCount(stuScore.size());
                teacherMarkVO.setExpMarkAverage(calculateHelper.calculateArrangeScore((expScore)));

                teacherMarkVO.setFinalAverage(calculateHelper.calculateFinalScore(
                        teacherMarkVO.getStuMarkAverage(),
                        teacherMarkVO.getTeaMarkAverage(),
                        teacherMarkVO.getExpMarkAverage()));

                teacherMarkVOList.add(teacherMarkVO);
            });
        });

        return new PageResult<>(pageRequestDTO.getPageSize(),
                pageRequestDTO.getPageCurrent(),
                teacherMarkVOList.size(),
                teacherMarkVOList);
    }
    @Override
    public XSSFWorkbook getMarkReport(PageRequestDTO pageRequestDTO) {
        return createExcelInfo(getAllTeacherMark(pageRequestDTO).getPageData());
    }


    private XSSFWorkbook createExcelInfo(List<TeacherMarkVO> records) {

        //创建一个工作表
        XSSFWorkbook workbook = new XSSFWorkbook();
        String name = DateUtil.parseToString(new Date(), "yyyy-MM-dd HH-mm-ss");
        XSSFSheet sheet = workbook.createSheet(name);
        //添加表头
        XSSFRow xssfRow = sheet.createRow(0);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        //表头格式
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headCellStyle.setFont(font);
        //自动换行
        headCellStyle.setWrapText(true);
        //数据格式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //自动换行
        cellStyle.setWrapText(true);
        int column = 0;

        //添加表头内容
        XSSFCell headCell = xssfRow.createCell(column);
        headCell.setCellValue("教师姓名");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("评分课程");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("学生评分人数");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("学生评分均分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("教师评分人数");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("教师评分均分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("专家评分人数");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("专家评分均分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("最终评分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);


        int row = 1;
        //填充数据
        for (TeacherMarkVO teacherMarkVO : records) {
            column = 0;
            xssfRow = sheet.createRow(row);

            XSSFCell cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getTeacherName());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getCourseName());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getStuMarkAverage());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getStuMarkCount());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getTeaMarkCount());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getTeaMarkAverage());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getExpMarkCount());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getExpMarkAverage());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getFinalAverage());
            cell.setCellStyle(cellStyle);

            row++;
        }
        return workbook;
    }
}
