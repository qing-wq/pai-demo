package ink.whi.service.user.repo.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import ink.whi.service.article.repo.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * 机器IP
     */
    private String host;

    /**
     * 访问计数
     */
    private Integer cnt;

    /**
     * 当前日期
     */
    private Date date;
}
