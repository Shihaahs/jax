package com.hj.jax.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.hj.jax.core.common.enums.UserPermissionEnum;
import com.hj.jax.core.common.page.PageResult;
import com.hj.jax.core.common.request.PageRequestDTO;
import com.hj.jax.core.common.util.DateUtil;
import com.hj.jax.core.common.vo.TeacherMarkVO;
import com.hj.jax.core.dal.domain.Mark;
import com.hj.jax.core.dal.domain.MarkEngine;
import com.hj.jax.core.dal.domain.User;
import com.hj.jax.core.dal.manager.MarkEngineManager;
import com.hj.jax.core.dal.manager.MarkManager;
import com.hj.jax.core.dal.manager.UserManager;
import com.hj.jax.core.service.WebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.util.*;

import static com.hj.jax.core.common.enums.MarkTypeEnum.*;
import static com.hj.jax.core.common.page.PageQuery.conditionAdapter;
import static com.hj.jax.core.common.page.PageQuery.initPage;

@Slf4j
@Service
public class WebServiceImpl implements WebService {

    private static final Integer COLUMN_WIDTH = 25 * 256;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00"); //平均分保留两位小数点

    @Autowired
    private UserManager userManager;
    @Autowired
    private MarkManager markManager;
    @Autowired
    private MarkEngineManager engineManager;


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
    public PageResult<Mark> getAllMark(PageRequestDTO pageRequestDTO) {
        //分页条件查询
        Page<Mark> markPage = markManager.selectPage(
                initPage(pageRequestDTO),
                conditionAdapter(pageRequestDTO));

        return new PageResult<>(pageRequestDTO.getPageSize(),
                pageRequestDTO.getPageCurrent(),
                (int) markPage.getTotal(),
                markPage.getRecords());
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
            TeacherMarkVO teacherMarkVO = new TeacherMarkVO();
            teacherMarkVO.setTeacherId(teacher.getUserId());
            teacherMarkVO.setTeacherName(teacher.getUserName());

            //获取评分分
            List<Integer> stuScore = markManager.getMarkByTeacherId(teacher.getUserId(), STUDENT_MARK.getCode());
            List<Integer> teaScore = markManager.getMarkByTeacherId(teacher.getUserId(), TEACHER_MARK.getCode());
            List<Integer> expScore = markManager.getMarkByTeacherId(teacher.getUserId(), EXPERT_MARK.getCode());

            teacherMarkVO.setStuArrangeScore(calculateArrangeScore((stuScore)));
            teacherMarkVO.setTeaArrangeScore(calculateArrangeScore((teaScore)));
            teacherMarkVO.setExpArrangeScore(calculateArrangeScore((expScore)));

            teacherMarkVO.setFinalScore(calculateFinalScore(
                    teacherMarkVO.getStuArrangeScore(),
                    teacherMarkVO.getTeaArrangeScore(),
                    teacherMarkVO.getExpArrangeScore()));

            teacherMarkVOList.add(teacherMarkVO);
        });


        return new PageResult<>(pageRequestDTO.getPageSize(),
                pageRequestDTO.getPageCurrent(),
                teacherMarkVOList.size(),
                teacherMarkVOList);
    }

    /**
     * <p> 计算平均分 </p>
     *
     * @param scoreList
     * @return Integer
     * @author Wuer (wuer@maihaoche.com)
     * @date 2019/4/29 10:45
     * @since V1.0.0-SNAPSHOT
     */
    private String calculateArrangeScore(List<Integer> scoreList) {
        Assert.notNull(scoreList, "calculateArrangeScore - 计算平均分的分数集合为空");
        OptionalDouble score = scoreList.stream().mapToDouble(x -> x).average();
        if (score.isPresent()) {
            return decimalFormat.format(score.getAsDouble());
        }
        log.warn("calculateArrangeScore - 计算平均分的分数异常 -> 0.00");
        return decimalFormat.format(score.orElse(0.00F));
    }


    /**
     * <p> 根据权重和平均分，计算最终得分 </p>
     *
     * @return Integer
     * @author Wuer (wuer@maihaoche.com)
     * @date 2019/4/29 10:45
     * @since V1.0.0-SNAPSHOT
     */
    private String calculateFinalScore(String stuScore, String teaScore, String expScore) {
        Assert.notNull(stuScore, "calculateFinalScore - 计算最终得分的学生平均分为空");
        Assert.notNull(teaScore, "calculateFinalScore - 计算最终得分的教师平均分为空");
        Assert.notNull(expScore, "calculateFinalScore - 计算最终得分的专家平均分为空");
        double stu, tea, exp;
        stu = stuScore.isEmpty() ? 0F : Double.valueOf(stuScore);
        tea = teaScore.isEmpty() ? 0F : Double.valueOf(teaScore);
        exp = expScore.isEmpty() ? 0F : Double.valueOf(expScore);

        MarkEngine engine = engineManager.getMarkEngine();
        double stuWeight = (float) engine.getStudentWeight() / 100;
        double teaWeight = (float) engine.getTeacherWeight() / 100;
        double expWeight = (float) engine.getExpertWeight() / 100;

        double finalScore = stu * stuWeight + tea * teaWeight + exp * expWeight;

        return decimalFormat.format(finalScore);
    }

    @Override
    public XSSFWorkbook getMarkReport(PageRequestDTO pageRequestDTO) {
        return createExcelInfo(getAllTeacherMark(pageRequestDTO).getPageData());
    }


    private XSSFWorkbook createExcelInfo(List<TeacherMarkVO> records) {

        //创建一个工作表
        XSSFWorkbook workbook = new XSSFWorkbook();
        String name = DateUtil.parseToString(new Date(), "yyyy-MM-dd HH:mm:ss");
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
        headCell.setCellValue("学生评分平均分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("教师评分平均分");
        headCell.setCellStyle(headCellStyle);
        sheet.setColumnWidth(column, COLUMN_WIDTH);
        column++;

        headCell = xssfRow.createCell(column);
        headCell.setCellValue("专家评分平均分");
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
            cell.setCellValue(teacherMarkVO.getStuArrangeScore());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getTeaArrangeScore());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getExpArrangeScore());
            cell.setCellStyle(cellStyle);
            column++;

            cell = xssfRow.createCell(column);
            cell.setCellValue(teacherMarkVO.getFinalScore());
            cell.setCellStyle(cellStyle);

            row++;
        }
        return workbook;
    }
}
