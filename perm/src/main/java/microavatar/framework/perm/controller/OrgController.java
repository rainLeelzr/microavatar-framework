/***********************************************************************
 * @模块: 组织业务逻辑实现
 * @模块说明: 组织模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.Org;
import microavatar.framework.perm.dao.OrgDao;
import microavatar.framework.perm.service.OrgService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/perm/org")
public class OrgController extends BaseController<OrgService, OrgDao, Org> {

    @Resource
    private OrgService orgService;

    @Override
    protected OrgService getService() {
        return orgService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public Org getById(@PathVariable Long id) {
        return super.getById(id);
    }

}