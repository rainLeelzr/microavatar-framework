/***********************************************************************
 * @模块: 用户业务逻辑实现
 * @模块说明: 用户模块服务
 ***********************************************************************/

package microavatar.framework.perm.controller;

import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.mvc.BaseController;
import microavatar.framework.perm.criteria.UserCriteria;
import microavatar.framework.perm.dao.UserDao;
import microavatar.framework.perm.entity.User;
import microavatar.framework.perm.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perm/user")
@Slf4j
public class UserController extends BaseController<UserCriteria, UserDao, User, UserService> {

    @Resource
    private UserService userService;

    private List<Long> cache;

    @Override
    protected UserService getService() {
        return userService;
    }

    //@Override
    //@RequestMapping("/id/{id}")
    //public User getById(@PathVariable Long id) {
    //    return super.getById(id);
    //}

    @GetMapping("/t/{loop}")
    public void test(@PathVariable("loop") int loop) {
        long startTime = System.currentTimeMillis();
        Long start = 1000L;
        cache = new ArrayList<>(loop);

        int count = 0;
        for (int i = 0; i < loop; i++) {
            count++;
            cache.add(++start);
        }

        long endTime = System.currentTimeMillis();
        log.info("count:{},cache.size:{},loop:{},cost:{}ms", count, cache.size(), loop, endTime - startTime);
    }

}