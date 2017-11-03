/***********************************************************************
 * @实体: 错误信息
 * @实体说明:
 * 错误信息表，存放系统所有的自定义错误信息，系统初始化时，会加载全部信息到内存
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.base.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class ErrorInfo extends BaseEntity {

    /**
     * key
     * 数据库类型：varchar(64)
     * 功能说明:
     * 错误代码的key
     */
    private String keyStr;

    /**
     * 代码
     * 数据库类型：int
     * 功能说明:
     * 错误代码
     */
    private Integer code;

    /**
     * 信息
     * 数据库类型：varchar(128)
     * 功能说明:
     * 发生错误时，具体的错误信息
     */
    private String msg;

    public static final String KEYSTR = "keyStr";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"keyStr\":\"")
                .append(keyStr).append('\"')
                .append(",\"code\":")
                .append(code)
                .append(",\"msg\":\"")
                .append(msg).append('\"')
                .append('}')
                .toString();
    }
}