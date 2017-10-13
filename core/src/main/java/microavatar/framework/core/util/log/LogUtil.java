package microavatar.framework.core.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务日志工具
 */
public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static Logger getLogger() {
        return logger;
    }

}