/***********************************************************************
 * @模块: 组织业务逻辑实现
 * @模块说明: 组织模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.OrgDao;
import microavatar.framework.auth.entity.Org;
import microavatar.framework.auth.service.OrgService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/org")
public class OrgController extends BaseController<OrgService, OrgDao, Org> {

    @Resource
    private OrgService orgService;

    @Override
    protected OrgService getService() {
        return orgService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public Org getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}