/***********************************************************************
 * @实体: 系统资源
 * @实体说明:
 * 资源，包括菜单(页面)、按钮等可以访问的资源
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.auth.entity;

import microavatar.framework.common.BaseEntity;

public class SysResource extends BaseEntity {

    /**
     * 父id
     * 数据库类型：varchar(36)
     */
    private String parentId;

    /**
     * 名称
     * 数据库类型：varchar(64)
     */
    private String name;

    /**
     * 类型
     * 数据库类型：tinyint
     */
    private Byte type;

    /**
     * 域名或ip
     * 数据库类型：varchar(64)
     */
    private String host;

    /**
     * 路径
     * 数据库类型：varchar(256)
     */
    private String url;

    /**
     * 顺序
     * 数据库类型：tinyint
     */
    private Byte orderNum;

    /**
     * 启用
     * 数据库类型：tinyint
     */
    private Byte enabled;

    /**
     * 备注
     * 数据库类型：varchar(4096)
     */
    private String remark;

    public static final String PARENTID = "parentId";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String HOST = "host";

    public static final String URL = "url";

    public static final String ORDERNUM = "orderNum";

    public static final String ENABLED = "enabled";

    public static final String REMARK = "remark";

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Byte orderNum) {
        this.orderNum = orderNum;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"id\":\"")
                .append(id).append('\"')
                .append(",\"parentId\":\"")
                .append(parentId).append('\"')
                .append(",\"name\":\"")
                .append(name).append('\"')
                .append(",\"type\":")
                .append(type)
                .append(",\"host\":\"")
                .append(host).append('\"')
                .append(",\"url\":\"")
                .append(url).append('\"')
                .append(",\"orderNum\":")
                .append(orderNum)
                .append(",\"enabled\":")
                .append(enabled)
                .append(",\"remark\":\"")
                .append(remark).append('\"')
                .append('}')
                .toString();
    }
}