package microavatar.framework.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@Controller()
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping("test")
    public String test(){
        return "test";
    }

}
