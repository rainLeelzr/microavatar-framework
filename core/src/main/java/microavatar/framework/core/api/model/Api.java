package microavatar.framework.core.api.model;

import org.springframework.web.bind.annotation.RequestMethod;

public class Api {

    /**
     * api对应的方法
     */
    private String methodName;

    /**
     * api方法的返回类型
     */
    private String returnType;

    /**
     * 此方法接受的请求method
     */
    private RequestMethod[] requestMethods;

    /**
     * 按“/"分割的url
     */
    private String[] urlDivisions;

    /**
     * api参数对应的protobuf限定类名
     */
    private String protobufC2S;

    private String protobufS2C;

    public String getProtobufC2S() {
        return protobufC2S;
    }

    /**
     * 判断是不是void返回值
     */
    public boolean isVoid() {
        return "void".equals(this.returnType);
    }

    public void setProtobufC2S(String protobufC2S) {
        this.protobufC2S = protobufC2S;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getUrlDivisions() {
        return urlDivisions;
    }

    public void setUrlDivisions(String[] urlDivisions) {
        this.urlDivisions = urlDivisions;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    public String getProtobufS2C() {
        return protobufS2C;
    }

    public void setProtobufS2C(String protobufS2C) {
        this.protobufS2C = protobufS2C;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
