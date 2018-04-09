package microavatar.framework.core.net.tcp.request;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.session.Session;

/**
 * 一个网络包请求就是一个系统的事件.类似一个task任务
 *
 * @author Rain
 */
@Slf4j
public class Request<P extends Package, S extends Session> {

    @Getter
    private final P packageData;

    @Getter
    private final S session;

    public Request(P packageData, S session) {
        this.packageData = packageData;
        this.session = session;
    }

}
