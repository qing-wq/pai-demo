package ink.whi.api.model.vo.article;

import ink.whi.api.model.enums.ArticleTypeEnum;
import ink.whi.api.model.enums.PushStatusEnum;
import ink.whi.api.model.enums.SourceTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 发布文章请求实体
 */
@Data
public class ArticlePostReq implements Serializable {

    private Long articleId;

    private String title;

    private String shortTitle;

    // 分类
    private Long categoryId;

    private Set<Long> tagIds;

    // 简介
    private String summary;

    private String content;

    // 封面
    private String cover;

    /**
     * 文本类型
     *
     * @see ArticleTypeEnum
     */
    private String articleType;

    /**
     * 来源：1-转载，2-原创，3-翻译
     *
     * @see SourceTypeEnum
     */
    private Integer source;

    /**
     * @see PushStatusEnum
     */
    private Integer status;

    /**
     * 原文地址
     */
    private String sourceUrl;

    /**
     *  POST 发表, SAVE 暂存 DELETE 删除
     */
    private String actionType;

    public PushStatusEnum pushStatus() {
        if ("post".equalsIgnoreCase(actionType)) {
            return PushStatusEnum.ONLINE;
        } else {
            return PushStatusEnum.OFFLINE;
        }
    }

    public boolean deleted() {
        return "delete".equalsIgnoreCase(actionType);
    }
}
