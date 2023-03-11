package ink.whi.web.admin.rest;

import ink.whi.api.model.context.ReqInfoContext;
import ink.whi.api.model.enums.StatusEnum;
import ink.whi.api.model.exception.ExceptionUtil;
import ink.whi.api.model.vo.ResVo;
import ink.whi.api.model.vo.user.dto.BaseUserInfoDTO;
import ink.whi.core.permission.Permission;
import ink.whi.core.permission.UserRole;
import ink.whi.service.user.service.SessionService;
import ink.whi.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


/**
 * 后台登录接口
 */
@RestController
@RequestMapping(path = {"/api/admin/login", "/admin/login"})
public class AdminLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @PostMapping(path = {"", "/"})
    public ResVo<BaseUserInfoDTO> login(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw ExceptionUtil.of(StatusEnum.ILLEGAL_ARGUMENTS);
        }
        BaseUserInfoDTO info = userService.passwordLogin(username, password);
        // todo: 将userId存入session并放在Cookie中返回给客户端
        String session = info.getUserId().toString();
        if (StringUtils.isNotBlank(session)) {
            // 写入Cookie
            resp.addCookie(new Cookie(SessionService.SESSION_KEY, session));
            return ResVo.ok(info);
        } else {
            return ResVo.fail(StatusEnum.LOGIN_FAILED_MIXED, "登录失败，请重试");
        }
    }

    @Permission(role = UserRole.LOGIN) // todo: 添加AOP实体
    @RequestMapping("/logout")
    public ResVo<Boolean> logOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional.ofNullable(ReqInfoContext.getReqInfo()).ifPresent(s -> sessionService.logout(s.getSession()));
        resp.sendRedirect("/");
        return ResVo.ok(true);
    }
}
