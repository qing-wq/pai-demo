package ink.whi.api.model.exception;

import ink.whi.api.model.enums.StatusEnum;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtil {


    public static ForumException of(StatusEnum status, Object... args) {
        return new ForumException(status, args);
    }
}
