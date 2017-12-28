/***********************************************************************
 * @实体: 用户
 * @实体说明:
 * 1:不存放基本的用户信息，与业务无关。
 * 2:用户可以采用用户名密码、微信、新浪等方式登录或绑定账号，但是user只有一条记录，因为他们是同一个用户
 * 3:如果业务系统需要更多userInfo里没有的用户信息，需要自己定义userExt扩展表来记录用户的信息
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import lombok.*;
import microavatar.framework.core.mvc.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<User> {

    // 数据库字段名
    public static final String ACCOUNT = "account";
    public static final String PWD = "pwd";
    public static final String NAME = "name";
    public static final String STATUS = "status";

    /**
     * 账号
     * 账号/密码模式登录时，输入的账号
     * 数据库类型：varchar(16)
     */
    private String account;

    /**
     * 密码
     * 账号/密码模式登录时，输入的密码
     * 数据库类型：varchar(32)
     */
    private String pwd;

    /**
     * 名称
     * 数据库类型：varchar(16)
     * 功能说明:
     * 取值为第一次登录系统时所携带的昵称
     * 例如第一次采用微信登录，则此字段赋值为微信登录的昵称
     * 以后次登录时，该用户采用新浪账号绑定并登录，此时本字段不变。
     * 用户的后台个人信息页面中，显示的昵称为本字段，本字段可供用户主动修改
     * 因“昵称”为常用字段，所以放在本类，以便业务快速获取
     */
    private String name;

    /**
     * 状态
     * 数据库类型：tinyint
     */
    private Byte status;

    @Override
    public User randomEntity() {
        this.randomId();
        this.createTime = System.currentTimeMillis();
        this.modifyTime = System.currentTimeMillis();
        this.deleted = randomBoolean();

        this.account = randomString();
        this.pwd = randomString();
        this.name = randomString();
        this.status = randomByte();
        return this;
    }

    @Override
    public String toString() {
        return "{\"User\":{"
                + "\"id\":\"" + id + "\""
                + ", \"account\":\"" + account + "\""
                + ", \"pwd\":\"" + pwd + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"status\":\"" + status + "\""
                + ", \"createTime\":\"" + createTime + "\""
                + ", \"modifyTime\":\"" + modifyTime + "\""
                + ", \"deleted\":\"" + deleted + "\""
                + "}}";
    }
}