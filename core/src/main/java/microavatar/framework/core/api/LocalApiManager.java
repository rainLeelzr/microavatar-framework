package microavatar.framework.core.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class LocalApiManager implements ApplicationContextAware {

    private ServerApi serverApi;

    public ServerApi getServerApi() {
        return serverApi;
    }

    private ApplicationContext applicationContext;

    /**
     * 需要在所有spring bean加载完后，才调用本方法
     */
    public void init() {
        log.info("正在初始化ApiManager...");
        try {
            Map<String, List<Api>> tempApis = new HashMap<>();

            // 遍历所有含有@RequestCmd注解的bean
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
            String[] emptyArray = new String[]{""};
            for (String beanDefinitionName : beanDefinitionNames) {
                Object bean = applicationContext.getBean(beanDefinitionName);

                Class<?> beanClass = bean.getClass();

                RequestMapping classRequestMapping = beanClass.getAnnotation(RequestMapping.class);

                String[] classPaths = classRequestMapping == null
                        ? emptyArray
                        : classRequestMapping.value().length == 0
                        ? classRequestMapping.path().length == 0 ? emptyArray : classRequestMapping.path()
                        : classRequestMapping.value();

                Method[] declaredMethods = beanClass.getDeclaredMethods();
                for (Method targetApiMethod : declaredMethods) {
                    RequestMapping methodRequestMapping = targetApiMethod.getAnnotation(RequestMapping.class);
                    if (methodRequestMapping == null) {
                        continue;
                    }

                    Protobuf protoAnnotation = targetApiMethod.getAnnotation(Protobuf.class);

                    String[] methodPaths = methodRequestMapping.value().length == 0
                            ? methodRequestMapping.path().length == 0 ? emptyArray : methodRequestMapping.path()
                            : methodRequestMapping.value();

                    // 拼装url
                    for (String classPath : classPaths) {
                        for (String methodPath : methodPaths) {
                            String url = classPath + methodPath;
                            // 过滤掉空内容的url
                            if (url.length() == 0) {
                                log.info("url为空，已过滤：{}", targetApiMethod);
                                continue;
                            }
                            List<Api> urlApis = tempApis.computeIfAbsent(url, k -> new ArrayList<>());
                            for (Api urlApi : urlApis) {
                                if (urlApi.getRequestMethods() == methodRequestMapping.method()) {
                                    // 这个url可能会重复，但在一般的业务逻辑中，我们不会写出重复的url。在spring提供的功能中，会有重复url
                                    // 例如BasicErrorController的@RequestMapping(produces = "text/html")和@RequestMapping
                                    log.debug(
                                            "方法[{}]的requestMapping与[{}]重复, 请注意",
                                            targetApiMethod.toString(),
                                            urlApi.getMethodName());
                                }
                            }

                            Api api = new Api();
                            api.setMethodName(targetApiMethod.toString());

                            api.setRequestMethods(methodRequestMapping.method());
                            api.setProtobufC2S(protoAnnotation == null ? "" : protoAnnotation.c2s());
                            api.setProtobufS2C(protoAnnotation == null ? "" : protoAnnotation.s2c());
                            api.setUrlDivisions(url.split("/"));
                            Class<?> returnType = targetApiMethod.getReturnType();
                            api.setReturnType(returnType.toString());
                            urlApis.add(api);

                            log.debug("加载到{}", url);
                        }
                    }
                }
            }

            this.serverApi = new ServerApi(System.currentTimeMillis(), tempApis);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public long getInitTime() {
        return this.serverApi == null ? 0 : this.serverApi.getTime();
    }
}
