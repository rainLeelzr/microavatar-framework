/***********************************************************************
 * @模块: 参数业务逻辑实现
 * @模块说明: 参数模块服务
 ***********************************************************************/

package microavatar.framework.base.controller;

import microavatar.framework.base.dao.ParamDao;
import microavatar.framework.base.entity.Param;
import microavatar.framework.base.service.ParamService;
import microavatar.framework.core.mvc.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/base/param")
public class ParamController extends BaseController<ParamService, ParamDao, Param> {

    @Resource
    private ParamService paramService;

    @Override
    protected ParamService getService() {
        return paramService;
    }

    @Override
    @RequestMapping("/id/{id}")
    public Param getById(@PathVariable Long id) {
        return super.getById(id);
    }

}