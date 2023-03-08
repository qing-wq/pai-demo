package ink.whi.service.user.service;

import ink.whi.api.model.vo.user.UserInfoSaveReq;
import ink.whi.api.model.vo.user.UserSaveReq;
import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import ink.whi.api.model.vo.user.dto.UserStatisticInfoDTO;

public interface UserService {

    /**
     * 账号密码方式登录；适用于admin后台登录
     *
     * @param userName
     * @param password
     * @return
     */
    BaseUserInfoDTO passwordLogin(String userName, String password);

    /**
     * 用户存在时，直接返回；不存在时，则初始化
     *
     * @param req
     */
    void registerOrGetUserInfo(UserSaveReq req);

    /**
     * 保存用户详情
     *
     * @param req
     */
    void saveUserInfo(UserInfoSaveReq req);

    /**
     * 查询用户基本信息
     * todo: 可以做缓存优化
     *
     * @param userId
     * @return
     */
    BaseUserInfoDTO queryBasicUserInfo(Long userId);


    /**
     * 查询用户主页信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId);
}

