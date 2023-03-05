package ink.whi.service.article.repo.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.api.model.vo.article.dto.ArticleDTO;
import ink.whi.service.article.repo.entity.ArticleDO;
import ink.whi.service.article.repo.mapper.ArticleDetailMapper;
import ink.whi.service.article.repo.mapper.ArticleMapper;
import ink.whi.service.article.repo.mapper.ReadCountMapper;

import javax.annotation.Resource;
import java.util.Objects;

public class ArticleDao extends ServiceImpl<ArticleMapper, ArticleDO> {

    @Resource
    private ArticleDetailMapper articleDetailMapper;
    @Resource
    private ReadCountMapper readCountMapper;

    public ArticleDTO queryArticleDetail(Long articleId) {
        // 查询文章记录
        ArticleDO article = baseMapper.selectById(articleId);
        if (article == null || Objects.equals(article.getDeleted() == 0, )) {

        }
    }
}
