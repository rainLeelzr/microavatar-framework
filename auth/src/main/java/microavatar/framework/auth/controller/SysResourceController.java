/***********************************************************************
 * @模块: 系统资源业务逻辑实现
 * @模块说明: 系统资源模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.SysResourceDao;
import microavatar.framework.auth.entity.SysResource;
import microavatar.framework.auth.service.SysResourceService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/sysResource")
public class SysResourceController extends BaseController<SysResourceService, SysResourceDao, SysResource> {

    @Resource
    private SysResourceService sysResourceService;

    @Override
    protected SysResourceService getService() {
        return sysResourceService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public SysResource getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}