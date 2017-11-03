/***********************************************************************
 * @模块: 系统资源业务逻辑实现
 * @模块说明: 系统资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.SysResource;
import microavatar.framework.perm.dao.SysResourceDao;
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