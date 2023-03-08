package ink.whi.service.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.api.model.vo.PageParam;
import ink.whi.api.model.vo.article.dto.ArticleFootCountDTO;
import ink.whi.api.model.vo.article.dto.SimpleUserInfoDTO;
import ink.whi.service.user.repo.Entity.UserFootDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFootMapper extends BaseMapper<UserFootDO> {

    /**
     * 查询文章计数信息
     * @param articleId
     * @return
     */
    ArticleFootCountDTO countArticleByArticleId(@Param("articleId") Long articleId);

    /**
     * 查询作者的文章的计数信息
     * @param author
     * @return
     */
    ArticleFootCountDTO countArticleByUserId(@Param("userId") Long author);

    /**
     * 查询用户收藏的文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    List<Long> listCollectedArticlesByUserId(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);


    /**
     * 查询用户阅读的文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    List<Long> listReadArticleByUserId(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);

    /**
     * 查询文章的点赞列表
     *
     * @param documentId
     * @param type
     * @param size
     * @return
     */
    List<SimpleUserInfoDTO> listSimpleUserInfosByArticleId(@Param("documentId") Long documentId,
                                                           @Param("type") Integer type,
                                                           @Param("size") int size);
}
