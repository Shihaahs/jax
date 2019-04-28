package com.hj.jax.core.common.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<T extends BaseModel> extends BaseMapper<T> {
    List<Long> selectIdPage(@Param("cm") Map<String, Object> var1);

    List<Long> selectIdPage(RowBounds var1, @Param("cm") Map<String, Object> var2);
}
