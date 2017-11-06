/***********************************************************************
 * @模块: 用户组织业务逻辑实现
 * @模块说明: 用户组织模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.UserOrg;
import microavatar.framework.perm.dao.UserOrgDao;
import microavatar.framework.perm.service.UserOrgService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/userOrg")
public class UserOrgController extends BaseController<UserOrgService, UserOrgDao, UserOrg> {

    @Resource
    private UserOrgService userOrgService;

    @Override
    protected UserOrgService getService() {
        return userOrgService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserOrg getById(@PathVariable String id) {
        return super.getById(id);
    }

}