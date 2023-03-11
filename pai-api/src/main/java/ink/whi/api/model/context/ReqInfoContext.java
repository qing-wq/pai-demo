package ink.whi.api.model.context;

import ink.whi.api.model.vo.SeoTagVo;
import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import lombok.Data;

import java.util.List;

/**
 * question: 请求上下文，携带用户身份相关信息
 *
 * @author YiHui
 * @date 2022/7/6
 */
public class ReqInfoContext {

    /**
     * fixme 注意，下面这种方式导致在子线程中拿不到用户信息
     */
    private static ThreadLocal<ReqInfo> contexts = new ThreadLocal<>();

    public static void addReqInfo(ReqInfo reqInfo) {
        contexts.set(reqInfo);
    }

    public static void clear() {
        contexts.remove();
    }

    public static ReqInfo getReqInfo() {
        return contexts.get();
    }

    @Data
    public static class ReqInfo {
        /**
         * appKey
         */
        private String appKey;
        /**
         * 访问的域名
         */
        private String host;
        /**
         * 访问路径
         */
        private String path;
        /**
         * 客户端ip
         */
        private String clientIp;
        /**
         * referer
         */
        private String referer;
        /**
         * post 表单参数
         */
        private String payload;
        /**
         * 设备信息
         */
        private String userAgent;

        /**
         * 登录的会话
         */
        private String session;

        /**
         * 用户id
         */
        private Long userId;
        /**
         * 用户信息
         */
        private BaseUserInfoDTO user;
        /**
         * 消息数量
         */
        private Integer msgNum;

        private List<SeoTagVo> seoList;
    }
}
