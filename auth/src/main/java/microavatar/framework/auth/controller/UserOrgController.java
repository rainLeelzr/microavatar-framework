/***********************************************************************
 * @模块: 用户组织业务逻辑实现
 * @模块说明: 用户组织模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.UserOrgDao;
import microavatar.framework.auth.entity.UserOrg;
import microavatar.framework.auth.service.UserOrgService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/userOrg")
public class UserOrgController extends BaseController<UserOrgService, UserOrgDao, UserOrg> {

    @Resource
    private UserOrgService userOrgService;

    @Override
    protected UserOrgService getService() {
        return userOrgService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserOrg getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}