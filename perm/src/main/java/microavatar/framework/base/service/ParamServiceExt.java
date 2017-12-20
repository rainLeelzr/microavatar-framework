/***********************************************************************
 * @模块: 参数业务逻辑实现
 * @模块说明: 参数模块服务
 ***********************************************************************/

package microavatar.framework.base.service;

import java.util.List;

// @Service
public class ParamServiceExt extends ParamService {

    @SuppressWarnings("unchecked")
    public <T> T getParamObjectByKey(String key, Class<T> clazz) {
        return null;
        // Object o = paramCache.get(key);
        // if (o == null) {
        //     return null;
        // }
        //
        // return (T) o;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getParamArrayByKey(String key, Class<T> clazz) {
        return null;
        // Object o = paramCache.get(key);
        // if (o == null) {
        //     return null;
        // }
        //
        // return (List<T>) o;
    }

}