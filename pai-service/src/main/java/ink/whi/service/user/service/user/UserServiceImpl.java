package ink.whi.service.user.service.user;

import ink.whi.api.model.enums.StatusEnum;
import ink.whi.api.model.exception.ExceptionUtil;
import ink.whi.api.model.exception.ForumException;
import ink.whi.api.model.vo.user.UserInfoSaveReq;
import ink.whi.api.model.vo.user.UserSaveReq;
import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import ink.whi.api.model.vo.user.dto.UserStatisticInfoDTO;
import ink.whi.service.user.repo.Entity.UserDO;
import ink.whi.service.user.repo.dao.UserDao;
import ink.whi.service.user.repo.dao.UserRelationDao;
import ink.whi.service.user.service.UserService;
import ink.whi.service.user.service.help.UserPwdEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserRelationDao userRelationDao;

    private UserPwdEncoder userPwdEncoder;

    @Override
    public BaseUserInfoDTO passwordLogin(String userName, String password) {
        UserDO user = userDao.getByUserName(userName);
        if (user == null) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS);
        }

        if (!userPwdEncoder.match(password, user.getPassword())) {
            throw ExceptionUtil.of(StatusEnum.USER_PWD_ERROR);
        }

        return queryBasicUserInfo(user.getId());
    }

    @Override
    public void registerOrGetUserInfo(UserSaveReq req) {

    }

    @Override
    public void saveUserInfo(UserInfoSaveReq req) {

    }

    @Override
    public BaseUserInfoDTO queryBasicUserInfo(Long userId) {
        return null;
    }

    @Override
    public UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId) {
        return null;
    }
}
