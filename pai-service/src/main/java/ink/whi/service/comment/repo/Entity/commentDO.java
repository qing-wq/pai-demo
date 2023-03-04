package ink.whi.service.comment.repo.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.service.article.repo.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("comment")
public class commentDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 顶级评论ID
     */
    private Long topCommentId;

    /**
     * 0未删除 1 已删除
     */
    private Integer deleted;

}
