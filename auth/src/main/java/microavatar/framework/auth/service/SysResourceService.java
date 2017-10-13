/***********************************************************************
 * @模块: 系统资源业务逻辑实现
 * @模块说明: 系统资源模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.SysResourceDao;
import microavatar.framework.auth.entity.SysResource;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysResourceService extends BaseService<SysResource, SysResourceDao> {

    @Resource
    private SysResourceDao sysResourceDao;

    @Override
    protected SysResourceDao getDao() {
        return sysResourceDao;
    }

}