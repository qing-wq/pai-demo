package ink.whi.service.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.api.model.vo.PageParam;
import ink.whi.api.model.vo.user.dto.FollowUserInfoDTO;
import ink.whi.service.user.repo.Entity.UserRelationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRelationMapper extends BaseMapper<UserRelationDO> {

    /**
     * 查询用户的关注
     * @param followUserId
     * @param pageParam
     * @return
     */
    List<FollowUserInfoDTO> queryUserFollowList(@Param("followUserId") Long followUserId, @Param("pageParam") PageParam pageParam);

    /**
     * 查询用户的粉丝
     * @param userId
     * @param pageParam
     * @return
     */
    List<FollowUserInfoDTO> queryUserFansList(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);

}
