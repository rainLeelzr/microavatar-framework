/***********************************************************************
 * @模块: 第三方系统用户业务逻辑实现
 * @模块说明: 第三方系统用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.ThirdPartyUser;
import microavatar.framework.perm.dao.ThirdPartyUserDao;
import microavatar.framework.perm.service.ThirdPartyUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/thirdPartyUser")
public class ThirdPartyUserController extends BaseController<ThirdPartyUserService, ThirdPartyUserDao, ThirdPartyUser> {

    @Resource
    private ThirdPartyUserService thirdPartyUserService;

    @Override
    protected ThirdPartyUserService getService() {
        return thirdPartyUserService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public ThirdPartyUser getById(@PathVariable Long id) {
        return super.getById(id);
    }

}