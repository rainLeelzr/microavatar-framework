package microavatar.framework.result;

/**
 * 方法、接口调用的返回结果
 */
public class Result {

    /**
     * 结果代码
     */
    private int code;

    /**
     * 结果的描述信息
     */
    private String msg;

    /**
     * 调用接口得到的数据
     */
    private Object data;

    public Result() {
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
