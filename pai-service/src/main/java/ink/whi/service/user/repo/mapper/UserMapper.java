package ink.whi.service.user.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.whi.service.user.repo.Entity.UserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserDO> {

    // 根据三方唯一id进行查询
    @Select("select * from user where third_account_id = #{accountId} limit 1")
    UserDO getByThirdAccountId(@Param("account_id") String accountId);
}
