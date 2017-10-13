/***********************************************************************
 * @模块: 错误信息业务逻辑实现
 * @模块说明: 错误信息模块服务
 ***********************************************************************/

package microavatar.framework.base.service;

import microavatar.framework.base.entity.ErrorInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ErrorInfoServiceExt extends ErrorInfoService {

    @Resource
    private ErrorInfoCache errorInfoCache;

    /**
     * /**
     * 根据key获取错误信息代码，不能返回null
     */
    public Integer getCodeByKey(String key) {
        return errorInfoCache.getCodeByKey(key);
    }

    /**
     * 根据key获取错误信息，不能返回null
     */
    public ErrorInfo getByKey(String key) {
        return errorInfoCache.getByKey(key);
    }
}