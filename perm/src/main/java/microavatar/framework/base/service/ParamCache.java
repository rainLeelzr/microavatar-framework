/***********************************************************************
 * @模块: 参数业务逻辑实现
 * @模块说明: 参数模块服务
 ***********************************************************************/

package microavatar.framework.base.service;

import com.alibaba.fastjson.JSON;
import microavatar.framework.base.dao.ParamDao;
import microavatar.framework.base.entity.Param;
import microavatar.framework.core.cache.memory.EntityCache;
import microavatar.framework.common.util.log.LogUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Component
public class ParamCache extends EntityCache<Param> {

    @Resource
    private ParamDao paramDao;

    private Map<String, Object> paramsMap;

    /**
     * 启用
     */
    public static Byte enable;

    /**
     * 禁用
     */
    public static Byte disable;

    private static final byte TYPE_STRING = 1;
    private static final byte TYPE_INT = 2;
    private static final byte TYPE_FLOAT = 3;
    private static final byte TYPE_BOOL = 4;
    private static final byte TYPE_STRING_ARRAY = 5;
    private static final byte TYPE_INT_ARRAY = 6;
    private static final byte TYPE_FLOAT_ARRAY = 7;
    private static final byte TYPE_BOOL_ARRAY = 8;

    @Override
    public List<Param> doLoad() {
        return paramDao.findAll();
    }

    @Override
    public boolean checkLoaded(List<Param> newEntitys) {
        if (CollectionUtils.isEmpty(newEntitys)) {
            LogUtil.getLogger().error("记录为空，请初始化好参数表的数据！！！");
            return false;
        }

        parseParams(newEntitys);
        return true;
    }

    private void parseParams(List<Param> params) {
        Map<String, Object> tempMap = new HashMap<>();
        params.forEach(param -> {
            String keyStr = param.getKeyStr();
            if (tempMap.containsKey(keyStr)) {
                throw new RuntimeException("Param含有重复的key：" + keyStr);
            }
            try {
                switch (param.getValueType()) {
                    case TYPE_STRING:
                        tempMap.put(keyStr, param.getValueStr());
                        break;
                    case TYPE_INT:
                        tempMap.put(keyStr, Integer.valueOf(param.getValueStr()));
                        break;
                    case TYPE_FLOAT:
                        tempMap.put(keyStr, Float.valueOf(param.getValueStr()));
                        break;
                    case TYPE_BOOL:
                        tempMap.put(keyStr, Boolean.valueOf(param.getValueStr()));
                        break;
                    case TYPE_STRING_ARRAY:
                        tempMap.put(keyStr, JSON.parseArray(param.getValueStr(), String.class));
                        break;
                    case TYPE_INT_ARRAY:
                        tempMap.put(keyStr, JSON.parseArray(param.getValueStr(), Integer.class));
                        break;
                    case TYPE_FLOAT_ARRAY:
                        tempMap.put(keyStr, JSON.parseArray(param.getValueStr(), Float.class));
                        break;
                    case TYPE_BOOL_ARRAY:
                        tempMap.put(keyStr, JSON.parseArray(param.getValueStr(), Boolean.class));
                        break;
                    default:
                        LogUtil.getLogger().error("解析参数失败，无法匹配到对应valueType：{}", param.toString());
                }
            } catch (Exception e) {
                LogUtil.getLogger().error("解析参数表失败：{}", param.toString(), e);
                throw e;
            }

        });
        paramsMap = tempMap;

        initConstParams();
    }

    private void initConstParams() {
        enable = ((Integer) paramsMap.get("global:enable")).byteValue();
        disable = ((Integer) paramsMap.get("global:disable")).byteValue();
    }

    public Object get(String key) {
        return paramsMap.get(key);
    }
}