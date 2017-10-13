/***********************************************************************
 * @模块: 用户信息业务逻辑实现
 * @模块说明: 用户信息模块服务
 ***********************************************************************/

package microavatar.framework.auth.controller;

import microavatar.framework.auth.dao.UserInfoDao;
import microavatar.framework.auth.entity.UserInfo;
import microavatar.framework.auth.service.UserInfoService;
import microavatar.framework.common.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth/userInfo")
public class UserInfoController extends BaseController<UserInfoService, UserInfoDao, UserInfo> {

    @Resource
    private UserInfoService userInfoService;

    @Override
    protected UserInfoService getService() {
        return userInfoService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public UserInfo getEntityById(@PathVariable String id) {
        return super.getEntityById(id);
    }

}