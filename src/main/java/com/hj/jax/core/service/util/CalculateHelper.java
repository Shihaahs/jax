package com.hj.jax.core.service.util;

import com.hj.jax.core.dal.domain.MarkEngine;
import com.hj.jax.core.dal.manager.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@Component
public class CalculateHelper {

    private static final Integer COLUMN_WIDTH = 25 * 256;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00"); //平均分保留两位小数点

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
    /**
     * <p> 计算平均分 </p>
     *
     * @param scoreList
     * @return Integer
     * @author Wuer (wuer@maihaoche.com)
     * @date 2019/4/29 10:45
     * @since V1.0.0-SNAPSHOT
     */
    public String calculateArrangeScore(List<Integer> scoreList) {
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
    public  String calculateFinalScore(String stuScore, String teaScore, String expScore) {
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

}
