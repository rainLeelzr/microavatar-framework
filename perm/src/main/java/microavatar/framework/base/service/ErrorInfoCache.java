package microavatar.framework.base.service;

import microavatar.framework.base.dao.ErrorInfoDao;
import microavatar.framework.base.entity.ErrorInfo;
import microavatar.framework.core.cache.memory.EntityCache;
import microavatar.framework.common.util.log.LogUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// @Component
public class ErrorInfoCache extends EntityCache<ErrorInfo> {

    @Resource
    ErrorInfoDao errorInfoDao;

    private Map<String, ErrorInfo> errorInfoMap;

    /**
     * 全局通用的操作成功
     */
    public static int SUCCESS;
    public static final String SUCCESS_KEY = "success";

    /**
     * 全局通用的操作失败
     */
    public static int FAIL;
    public static final String FAIL_KEY = "fail";

    @Override
    public List<ErrorInfo> doLoad() {
        return errorInfoDao.findAll();
    }

    @Override
    public boolean checkLoaded(List<ErrorInfo> newEntitys) {
        if (CollectionUtils.isEmpty(newEntitys)) {
            return false;
        }

        newEntitys
                .stream()
                .collect(Collectors.groupingBy(ErrorInfo::getKeyStr, Collectors.toList()))
                .forEach((key, value) -> {
                    if (value.size() > 1) {
                        throw new RuntimeException("ErrorInfo含有重复的key：" + key);
                    }
                });

        newEntitys
                .stream()
                .collect(Collectors.groupingBy(ErrorInfo::getCode, Collectors.toList()))
                .forEach((code, value) -> {
                    if (value.size() > 1) {
                        throw new RuntimeException("ErrorInfo含有重复的code：" + code);
                    }
                });

        parseErrorInfo(newEntitys);

        initConst();
        return true;
    }

    private void initConst() {
        SUCCESS = errorInfoMap.get(SUCCESS_KEY).getCode();
        FAIL = errorInfoMap.get(FAIL_KEY).getCode();
    }

    private void parseErrorInfo(List<ErrorInfo> newEntitys) {
        Map<String, ErrorInfo> tempErrorInfos = newEntitys
                .stream()
                .collect(Collectors.toMap(ErrorInfo::getKeyStr, Function.identity()));

        if (!tempErrorInfos.containsKey(SUCCESS_KEY)) {
            throw new RuntimeException("ErrorInfo没有key为{}的全局操作成功信息" + SUCCESS_KEY);
        }

        if (!tempErrorInfos.containsKey(FAIL_KEY)) {
            throw new RuntimeException("ErrorInfo没有key为{}的全局操作成功信息" + FAIL_KEY);
        }

        errorInfoMap = tempErrorInfos;
    }

    public Integer getCodeByKey(String key) {
        ErrorInfo errorInfo = errorInfoMap.get(key);
        if (errorInfo == null) {
            LogUtil.getLogger().error("无法找到key为{}的errorInfo，将默认返回失败的代码[{}]", key, FAIL);
            return FAIL;
        } else {
            return errorInfo.getCode();
        }
    }

    public ErrorInfo getByKey(String key) {
        ErrorInfo errorInfo = errorInfoMap.get(key);
        if (errorInfo == null) {
            LogUtil.getLogger().error("无法找到key为{}的errorInfo，将默认返回失败的错误信息", key, FAIL);
            return getByKey(FAIL_KEY);
        } else {
            return errorInfo;
        }
    }
}
