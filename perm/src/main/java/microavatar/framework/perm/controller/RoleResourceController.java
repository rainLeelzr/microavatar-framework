/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.RoleResource;
import microavatar.framework.perm.dao.RoleResourceDao;
import microavatar.framework.perm.service.RoleResourceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/roleResource")
public class RoleResourceController extends BaseController<RoleResourceService, RoleResourceDao, RoleResource> {

    @Resource
    private RoleResourceService roleResourceService;

    @Override
    protected RoleResourceService getService() {
        return roleResourceService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public RoleResource getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}