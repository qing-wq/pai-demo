package ink.whi.service.article.repo.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.api.model.context.ReqInfoContext;
import ink.whi.api.model.enums.PushStatusEnum;
import ink.whi.api.model.enums.YesOrNoEnum;
import ink.whi.api.model.vo.PageParam;
import ink.whi.api.model.vo.article.dto.ArticleDTO;
import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import ink.whi.core.permission.UserRole;
import ink.whi.service.article.repo.conveter.ArticleConverter;
import ink.whi.service.article.repo.entity.ArticleDO;
import ink.whi.service.article.repo.entity.ArticleDetailDO;
import ink.whi.service.article.repo.entity.CategoryDO;
import ink.whi.service.article.repo.mapper.ArticleDetailMapper;
import ink.whi.service.article.repo.mapper.ArticleMapper;
import ink.whi.service.article.repo.mapper.ReadCountMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ArticleDao extends ServiceImpl<ArticleMapper, ArticleDO> {

    @Resource
    private ArticleDetailMapper articleDetailMapper;
    @Resource
    private ReadCountMapper readCountMapper;

    /**
     * 查询文章内容
     *
     * @param articleId
     * @return
     */
    public ArticleDTO queryArticleDetail(Long articleId) {
        // 查询文章记录
        ArticleDO article = baseMapper.selectById(articleId);
        if (article == null || Objects.equals(article.getDeleted(), YesOrNoEnum.YES.getCode())) {
            return null;
        }

        // 查询正文
        ArticleDTO dto = ArticleConverter.toDto(article);
        if (showReviewContent(article)) {
            // 对于审核中的文章，只有作者和超管才能看
            ArticleDetailDO latestDetail = findLatestDetail(articleId);
            dto.setContent(latestDetail.getContent());
        } else {
            dto.setContent("### 文章审核中，请稍后再看！");
        }

        return dto;
    }

    private boolean showReviewContent(ArticleDO article) {
        if (article.getStatus() != PushStatusEnum.REVIEW.getCode()) {
            return true;
        }

        BaseUserInfoDTO user = ReqInfoContext.getReqInfo().getUser();
        if (user == null) {
            return false;
        }

        return user.getUserId().equals(article.getUserId()) || user.getRole().equalsIgnoreCase(UserRole.ADMIN.name());
    }

    /**
     * 查询文章最新的content
     *
     * @param articleId
     * @return
     */
    public ArticleDetailDO findLatestDetail(long articleId) {
        LambdaQueryWrapper<ArticleDetailDO> query = Wrappers.lambdaQuery();
        query.eq(ArticleDetailDO::getArticleId, articleId).eq(ArticleDetailDO::getDeleted, YesOrNoEnum.NO.getCode()).orderByDesc(ArticleDetailDO::getVersion);
        return articleDetailMapper.selectList(query).get(0);
    }

    /**
     * 保存文章正文
     * @param articleId
     * @param content
     * @return
     */
    public Long saveArticleContent(Long articleId, String content) {
        ArticleDetailDO detail = new ArticleDetailDO();
        detail.setArticleId(articleId);
        detail.setContent(content);
        detail.setVersion(1L);
        articleDetailMapper.insert(detail);
        return detail.getId();
    }

    /**
     * 更新文章
     * @param articleId
     * @param content
     * @param update true表示更新最后一条记录，false表示插入一条新的记录
     */
    public void updateArticleContent(Long articleId, String content, boolean update) {
        if (update) {
            articleDetailMapper.updateContent(articleId, content);
        } else {
            ArticleDetailDO detail = new ArticleDetailDO();
            detail.setArticleId(articleId);
            detail.setContent(content);
            detail.setVersion(1L);
            articleDetailMapper.insert(detail);
        }
    }

    /**
     * 查询文章列表
     * @param userId
     * @param pageParam
     * @return
     */
    public List<ArticleDO> listArticleByUserId(Long userId, PageParam pageParam) {
        LambdaQueryChainWrapper<ArticleDO> query = lambdaQuery().eq(ArticleDO::getUserId, userId)
                .eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last(PageParam.getLimitSql(pageParam))
                .orderByDesc(ArticleDO::getId);
        if (!Objects.equals(ReqInfoContext.getReqInfo().getUserId(), userId)) {
            // 如果不是作者本人，只能查看上线的文章
            query.eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode());
        }
        return baseMapper.selectList(query);
    }

    /**
     * 按分类查询
     * @param categoryId
     * @param pageParam
     * @return
     */
    public List<ArticleDO> listArticleByCategoryId(Long categoryId, PageParam pageParam) {
        if (categoryId != null && categoryId <= 0) {
            // categoryId 有误，查询所有
            categoryId = null;
        }
        LambdaQueryWrapper<ArticleDO> query = Wrappers.lambdaQuery();
        query.eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode());
        Optional.ofNullable(categoryId).ifPresent(cid -> query.eq(ArticleDO::getCategoryId, cid));
        query.last(PageParam.getLimitSql(pageParam))
                .orderByDesc(ArticleDO::getOfficalStat, ArticleDO::getToppingStat, ArticleDO::getCreateTime);
        return baseMapper.selectList(query);
    }

    /**
     * 查询分类的文章数量
     * @param categoryId
     * @return
     */
    public Long countArticleByCategoryId(Long categoryId) {
        LambdaQueryWrapper<ArticleDO> query = Wrappers.lambdaQuery();
        query.eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode())
                .eq(ArticleDO::getCategoryId, categoryId);
        return baseMapper.selectCount(query);
    }

    public Map<Long, Long> countArticleByCategoryId() {
        QueryWrapper<ArticleDO> query = Wrappers.query();

    }

}
