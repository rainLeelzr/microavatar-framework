/***********************************************************************
 * @模块: 参数业务逻辑实现
 * @模块说明: 参数模块服务
 ***********************************************************************/

package microavatar.framework.base.service;

import microavatar.framework.base.dao.ParamDao;
import microavatar.framework.base.entity.Param;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ParamService extends BaseService<Param, ParamDao> {

    @Resource
    private ParamDao paramDao;

    @Override
    protected ParamDao getDao() {
        return paramDao;
    }

}