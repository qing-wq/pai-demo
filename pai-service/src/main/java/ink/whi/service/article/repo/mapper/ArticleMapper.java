package ink.whi.service.article.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.service.article.repo.entity.ArticleDO;
import ink.whi.service.article.repo.entity.ReadCountDO;
import ink.whi.api.model.vo.article.dto.SimpleArticleDTO;
import ink.whi.api.model.vo.article.dto.YearArticleDTO;
import ink.whi.api.model.vo.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<ArticleDO> {

    /**
     * 通过id遍历文章, 用于生成sitemap.xml
     *
     * @param lastId
     * @param size
     * @return
     */
    List<SimpleArticleDTO> listArticlesOrderById(@Param("lastId") Long lastId, @Param("size") int size);

    /**
     * 根据阅读次数获取热门文章
     *
     * @param pageParam
     * @return
     */
    List<SimpleArticleDTO> listArticlesByReadCounts(@Param("pageParam") PageParam pageParam);

    /**
     * 查询作者的热门文章
     *
     * @param userId
     * @param pageParam
     * @return
     */
    List<SimpleArticleDTO> listArticlesByUserIdOrderByReadCounts(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);

    /**
     * 根据类目 + 标签查询文章
     *
     * @param category
     * @param tagIds
     * @param pageParam
     * @return
     */
    List<ReadCountDO> listArticleByCategoryAndTags(@Param("categoryId") Long category,
                                                   @Param("tags") List<Long> tagIds,
                                                   @Param("pageParam") PageParam pageParam);

    /**
     * 根据用户ID获取创作历程
     *
     * @param userId
     * @return
     */
    List<YearArticleDTO> listYearArticleByUserId(@Param("userId") Long userId);
}
