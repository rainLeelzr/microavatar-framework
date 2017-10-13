package microavatar.framework.im.model;

public class TestInput {

    private Integer toUserId;

    private String message;

    private Integer opt;

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOpt() {
        return opt;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"toUserId\":")
                .append(toUserId)
                .append(",\"message\":\"")
                .append(message).append('\"')
                .append(",\"opt\":")
                .append(opt)
                .append('}')
                .toString();
    }
}
