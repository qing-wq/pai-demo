package ink.whi.service.user.repo.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.api.model.enums.YesOrNoEnum;
import ink.whi.service.user.repo.Entity.UserDO;
import ink.whi.service.user.repo.Entity.UserFootDO;
import ink.whi.service.user.repo.Entity.UserInfoDO;
import ink.whi.service.user.repo.mapper.UserInfoMapper;
import ink.whi.service.user.repo.mapper.UserMapper;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDao extends ServiceImpl<UserInfoMapper, UserInfoDO> {

    @Resource
    private UserMapper userMapper;

    /**
     * 第三方账号登录
     * @param accountId
     * @return
     */
    public UserDO getByThirdAccountId(String accountId) {
        return userMapper.getByThirdAccountId(accountId);
    }

    /**
     * 用户名登录
     * @param userName
     * @return
     */
    public UserDO getByUserName(String userName) {
        LambdaQueryWrapper<UserDO> query = Wrappers.lambdaQuery();
        query.eq(UserDO::getUserName, userName)
                .eq(UserDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userMapper.selectOne(query);
    }

    public void saveUser(UserDO user) {
        userMapper.insert(user);
    }

    public UserInfoDO getByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectOne(query);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUserInfo(UserInfoDO user) {
        UserInfoDO record = getByUserId(user.getUserId());
        if (record.equals(user)) {
            return;
        }
        if (StringUtils.isEmpty(user.getPhoto())) {
            user.setPhoto(null);
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            user.setUserName(null);
        }
        user.setId(record.getId());
        updateById(user);
    }
}
