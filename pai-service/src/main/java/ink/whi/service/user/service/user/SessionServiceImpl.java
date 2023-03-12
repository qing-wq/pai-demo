package ink.whi.service.user.service.user;

import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import ink.whi.service.user.service.SessionService;

public class SessionServiceImpl implements SessionService {
    @Override
    public String getVerifyCode(String uuid) {
        return null;
    }

    @Override
    public String login(String code) {
        return null;
    }

    @Override
    public String login(Long userId) {
        return null;
    }

    @Override
    public void logout(String session) {

    }

    @Override
    public BaseUserInfoDTO getAndUpdateUserIpInfoBySessionId(String session, String clientIp) {
        return null;
    }
}
