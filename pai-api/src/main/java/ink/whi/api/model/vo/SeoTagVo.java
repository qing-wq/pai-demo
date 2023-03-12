package ink.whi.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YiHui
 * @date 2023/2/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
// question: SeoTagVo作用
public class SeoTagVo {

    private String key;

    private String val;
}
