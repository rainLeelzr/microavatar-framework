package microavatar.framework.goods.controller;

import microavatar.framework.goods.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("hello")
    public String hello() {
        String s = testService.sayHello();
        System.out.println(s);
        return s;
    }
}
