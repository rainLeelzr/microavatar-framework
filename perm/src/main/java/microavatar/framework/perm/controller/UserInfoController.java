/***********************************************************************
 * @模块: 用户信息业务逻辑实现
 * @模块说明: 用户信息模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.entity.UserInfo;
import microavatar.framework.perm.dao.UserInfoDao;
import microavatar.framework.perm.service.UserInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/perm/userInfo")
public class UserInfoController extends BaseController<UserInfoService, UserInfoDao, UserInfo> {

    @Resource
    private UserInfoService userInfoService;

    @Override
    protected UserInfoService getService() {
        return userInfoService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return super.getById(id);
    }

}