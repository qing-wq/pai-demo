package ink.whi.service.user.repo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.whi.api.model.enums.FollowStateEnum;
import ink.whi.api.model.vo.PageParam;
import ink.whi.api.model.vo.user.dto.FollowUserInfoDTO;
import ink.whi.service.user.repo.Entity.UserRelationDO;
import ink.whi.service.user.repo.mapper.UserRelationMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class UserRelationDao extends ServiceImpl<UserRelationMapper, UserRelationDO> {

    /**
     * 查询用户的关注
     *
     * @param followUserId
     * @param pageParam
     * @return
     */
    public List<FollowUserInfoDTO> listUserFollows(Long followUserId, PageParam pageParam) {
        return baseMapper.queryUserFollowList(followUserId, pageParam);
    }

    // 查询用户的粉丝
    public List<FollowUserInfoDTO> listUserFans(Long userId, PageParam pageParam) {
        return baseMapper.queryUserFansList(userId, pageParam);
    }

    /**
     * 查询指定粉丝的关注与给定用户列表的交集
     *
     * @param followUserId
     * @param targetUserId
     * @return
     */
    public List<UserRelationDO> listUserRelations(Long followUserId, Collection<Long> targetUserId) {
        return lambdaQuery().eq(UserRelationDO::getFollowUserId, followUserId)
                .in(UserRelationDO::getUserId, targetUserId).list();
    }

    /**
     * 查询用户的关注数
     *
     * @param followUserId
     * @return
     */
    public Long queryUserFollowCount(Long followUserId) {
        QueryWrapper<UserRelationDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserRelationDO::getFollowUserId, followUserId)
                .eq(UserRelationDO::getFollowState, FollowStateEnum.FOLLOW.getCode());
        return baseMapper.selectCount(query);
    }

    /**
     * 查询用户粉丝数
     *
     * @param userId
     * @return
     */
    public Long queryUserFansCount(Long userId) {
        return lambdaQuery()
                .eq(UserRelationDO::getUserId, userId)
                .eq(UserRelationDO::getFollowState, FollowStateEnum.FOLLOW.getCode()).count();
    }

    /**
     * 查询指定两个用户关系
     *
     * @param userId
     * @param followUserId
     * @return
     */
    public UserRelationDO getUserRelationByUserId(Long userId, Long followUserId) {
        return lambdaQuery()
                .eq(UserRelationDO::getUserId, userId)
                .eq(UserRelationDO::getFollowUserId, followUserId)
                .eq(UserRelationDO::getFollowState, FollowStateEnum.FOLLOW.getCode()).one();
    }

    /**
     * 查询用户关系记录
     * @param userId
     * @param followUserId
     * @return
     */
    public UserRelationDO getUserRelationRecord(Long userId, Long followUserId) {
        return lambdaQuery()
                .eq(UserRelationDO::getUserId, userId)
                .eq(UserRelationDO::getFollowUserId, followUserId).one();
    }
}
