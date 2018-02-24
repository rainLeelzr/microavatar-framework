package microavatar.framework.im.controller;

import microavatar.framework.im.model.TestInput;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(path = "/hello")
    public String hello(@RequestBody TestInput testInput) {
        System.out.println(testInput.toString());
        System.out.println("imServer: allMethod /test/hello");
        return "{\"status\":true}";
    }

    @RequestMapping(value = "/hello/{toUserId}", method = RequestMethod.POST)
    public String hello1(@PathVariable("toUserId") Integer toUserId) {

        System.out.println("toUserId:" + toUserId);
        System.out.println("imServer: GET /test/hello1");
        return "imServer: GET /test/hello1";
    }
}
