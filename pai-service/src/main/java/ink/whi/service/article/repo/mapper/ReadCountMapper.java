package ink.whi.service.article.repo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.service.article.repo.entity.ReadCountDO;
import org.springframework.stereotype.Component;

/**
 * 标签mapper接口
 *
 * @author louzai
 * @date 2022-07-18
 */
@Component
public interface ReadCountMapper extends BaseMapper<ReadCountDO> {
}
