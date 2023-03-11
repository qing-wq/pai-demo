package ink.whi.api.model.vo.article;

import lombok.Data;

import java.io.Serializable;

/**
 * Tag请求参数
 */
@Data
public class TagReq implements Serializable {

    /**
     * ID
     */
    private Long tagId;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 类目ID
     */
    private Long categoryId;
}
