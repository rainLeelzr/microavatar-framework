package microavatar.framework.core.api.model;

import java.util.List;
import java.util.Map;

public class ServerApi {

    /**
     * 初始化本微服务api的时间
     */
    private long time = 0;

    /**
     * key:   url
     * value: api
     */
    private Map<String, List<Api>> requestMappingApis;

    public ServerApi() {
    }

    public ServerApi(long time, Map<String, List<Api>> requestMappingApis) {
        this.time = time;
        this.requestMappingApis = requestMappingApis;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, List<Api>> getRequestMappingApis() {
        return requestMappingApis;
    }

    public void setRequestMappingApis(Map<String, List<Api>> requestMappingApis) {
        this.requestMappingApis = requestMappingApis;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"time\":")
                .append(time)
                .append(",\"requestMappingApis\":")
                .append(requestMappingApis)
                .append('}')
                .toString();
    }

}
