/***********************************************************************
 * @模块: 系统资源业务逻辑实现
 * @模块说明: 系统资源模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.SysResource;
import microavatar.framework.perm.dao.SysResourceDao;
import microavatar.framework.perm.service.SysResourceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/sysResource")
public class SysResourceController extends BaseController<SysResourceService, SysResourceDao, SysResource> {

    @Resource
    private SysResourceService sysResourceService;

    @Override
    protected SysResourceService getService() {
        return sysResourceService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public SysResource getById(@PathVariable String id) {
        return super.getById(id);
    }

}