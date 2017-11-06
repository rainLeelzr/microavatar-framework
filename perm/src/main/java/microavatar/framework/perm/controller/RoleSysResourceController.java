/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.RoleSysResource;
import microavatar.framework.perm.dao.RoleSysResourceDao;
import microavatar.framework.perm.service.RoleSysResourceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/roleSysResource")
public class RoleSysResourceController extends BaseController<RoleSysResourceService, RoleSysResourceDao, RoleSysResource> {

    @Resource
    private RoleSysResourceService roleSysResourceService;

    @Override
    protected RoleSysResourceService getService() {
        return roleSysResourceService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public RoleSysResource getById(@PathVariable String id) {
        return super.getById(id);
    }

}