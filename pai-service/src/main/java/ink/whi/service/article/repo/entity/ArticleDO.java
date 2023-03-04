package ink.whi.service.article.repo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.service.article.repo.Enum.PushStatusEnum;
import ink.service.article.repo.Enum.SourceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article")
public class ArticleDO extends BaseDO{
    private static final long serialVersionUID = 1L;

    /**
     * 作者
     */
    private Long userId;

    /**
     * 文章类型：1-博文，2-问答
     */
    private Integer articleType;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 短标题
     */
    private String shortTitle;

    /**
     * 文章头图
     */
    private String picture;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 来源：1-转载，2-原创，3-翻译
     *
     * @see SourceTypeEnum
     */
    private Integer source;

    /**
     * 原文地址
     */
    private String sourceUrl;

    /**
     * 状态：0-未发布，1-已发布
     *
     * @see PushStatusEnum
     */
    private Integer status;

    /**
     * 是否官方
     */
    private Integer officalStat;

    /**
     * 是否置顶
     */
    private Integer toppingStat;

    /**
     * 是否加精
     */
    private Integer creamStat;

    private Integer deleted;
}
