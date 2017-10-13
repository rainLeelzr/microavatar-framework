/***********************************************************************
 * @模块: 组织业务逻辑实现
 * @模块说明: 组织模块服务
 ***********************************************************************/

package microavatar.framework.auth.service;

import microavatar.framework.auth.dao.OrgDao;
import microavatar.framework.auth.entity.Org;
import microavatar.framework.common.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrgService extends BaseService<Org, OrgDao> {

    @Resource
    private OrgDao orgDao;

    @Override
    protected OrgDao getDao() {
        return orgDao;
    }

}