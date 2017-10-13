/***********************************************************************
 * @模块: 角色资源业务逻辑实现
 * @模块说明: 角色资源模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.RoleResourceDao;
import microavatar.framework.auth.entity.RoleResource;
import microavatar.framework.auth.service.RoleResourceService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/roleResource")
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