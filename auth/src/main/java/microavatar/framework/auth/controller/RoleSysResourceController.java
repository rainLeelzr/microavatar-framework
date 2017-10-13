/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.RoleSysResourceDao;
import microavatar.framework.auth.entity.RoleSysResource;
import microavatar.framework.auth.service.RoleSysResourceService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/roleSysResource")
public class RoleSysResourceController extends BaseController<RoleSysResourceService, RoleSysResourceDao, RoleSysResource> {

    @Resource
    private RoleSysResourceService roleSysResourceService;

    @Override
    protected RoleSysResourceService getService() {
        return roleSysResourceService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public RoleSysResource getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}