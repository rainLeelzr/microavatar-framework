package microavatar.framework.order.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("avatar-im")
public interface TestService {

    @RequestMapping("/test/hello")
    String sayHello();
}
