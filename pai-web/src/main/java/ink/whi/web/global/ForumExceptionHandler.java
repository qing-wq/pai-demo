package ink.whi.web.global;

import ink.whi.api.model.enums.StatusEnum;
import ink.whi.api.model.exception.ForumException;
import ink.whi.api.model.exception.Status;
import ink.whi.api.model.vo.ResVo;
import ink.whi.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.exception.ExceptionUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@ControllerAdvice
@Order(-100)
public class ForumExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        Status errStatus = buildToastMsg(e);

        // rest接口异常时返回json格式的异常数据，其他异常返回500页面
        if (restResponse(req, resp)) {
            if (resp.isCommitted()) {
                return new ModelAndView();
            }

            try {
                resp.reset();
                resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                resp.setHeader("Cache-Control", "no-cache, must-revalidate");
                resp.getWriter().println(JsonUtil.toStr(ResVo.fail(errStatus)));
                resp.getWriter().flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        String view = getErrorPage(errStatus, resp);
        ModelAndView mv = new ModelAndView(view);
        resp.setContentType(MediaType.TEXT_HTML_VALUE);
        // todo: SpringUtil GlobalInitService
//        mv.getModel().put("global", SpringUtil.getBean(GlobalInitService.class).globalAttr());
        mv.getModel().put("res", ResVo.fail(errStatus));
        mv.getModel().put("toast", JsonUtil.toStr(ResVo.fail(errStatus)));
        return mv;
    }

    private String getErrorPage(Status status, HttpServletResponse response) {
        if (StatusEnum.is5xx(status.getCode())) {
            response.setStatus(500);
            return "error/500";
        } else if (StatusEnum.is403(status.getCode())) {
            response.setStatus(403);
            return "error/403";
        } else {
            response.setStatus(404);
            return "error/404";
        }
    }

    public Status buildToastMsg(Exception ex) {
        if (ex instanceof ForumException) {
            return ((ForumException) ex).getStatus();
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return Status.newStatus(StatusEnum.RECORDS_NOT_EXISTS.getCode(), ExceptionUtils.getStackTrace(ex));
        } else if (ex instanceof NestedRuntimeException) {
            log.error("unexpect error", ex);
            return Status.newStatus(StatusEnum.UNEXPECT_ERROR, ex.getMessage());
        } else {
            log.error("unexpect error", ex);
            return Status.newStatus(StatusEnum.UNEXPECT_ERROR, ExceptionUtils.getStackTrace(ex));
        }
    }

    /**
     * 特定接口，返回json格式的异常信息
     *
     * @param request
     * @param response
     * @return
     */
    private boolean restResponse(HttpServletRequest request, HttpServletResponse response) {
        // 后台请求
        if (request.getRequestURI().startsWith("/api/admin") || request.getRequestURI().startsWith("/admin")) {
            return true;
        }

        // 图片上传接口
        if (request.getRequestURI().startsWith("/image/upload")) {
            return true;
        }

        if (response.getContentType() != null && response.getContentType().contains("json")) {
            return true;
        }

        // api请求
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (pathMatcher.match("/**/api/**", request.getRequestURI())) {
            return true;
        }
        return false;
    }
}
