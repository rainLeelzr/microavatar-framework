/***********************************************************************
 * @模块: 组织业务逻辑实现
 * @模块说明: 组织模块服务
 ***********************************************************************/

package microavatar.framework.perm.service;

import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.perm.entity.Org;
import microavatar.framework.perm.dao.OrgDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class OrgService extends BaseService<Org, OrgDao> {

    @Resource
    private OrgDao orgDao;

    @Override
    protected OrgDao getDao() {
        return orgDao;
    }

}