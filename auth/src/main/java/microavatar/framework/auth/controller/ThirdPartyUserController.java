/***********************************************************************
 * @模块: 第三方系统用户业务逻辑实现
 * @模块说明: 第三方系统用户模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.ThirdPartyUserDao;
import microavatar.framework.auth.entity.ThirdPartyUser;
import microavatar.framework.auth.service.ThirdPartyUserService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/thirdPartyUser")
public class ThirdPartyUserController extends BaseController<ThirdPartyUserService, ThirdPartyUserDao, ThirdPartyUser> {

    @Resource
    private ThirdPartyUserService thirdPartyUserService;

    @Override
    protected ThirdPartyUserService getService() {
        return thirdPartyUserService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public ThirdPartyUser getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}