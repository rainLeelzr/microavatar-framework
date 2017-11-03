/***********************************************************************
 * @实体: 用户信息
 * @实体说明:
 * 用户信息实体，存放基本的用户信息
 *
 * 注意：本类对应mysql数据库的表，由代码生成器自动生成，不允许手动修改本类任何内容，
 * 以避免需要增加或减少本类字段时，不能再由代码生成器自动生成，因为会覆盖手动修改的内容！
 ***********************************************************************/

package microavatar.framework.perm.entity;

import microavatar.framework.core.mvc.BaseEntity;

public class UserInfo extends BaseEntity {

    /**
     * 用户主键
     * 数据库类型：varchar(36)
     */
    private String userId;

    /**
     * 真实名称
     * 数据库类型：varchar(16)
     */
    private String realName;

    /**
     * 性别
     * 数据库类型：tinyint
     *
     * @see avatar.rain.auth.status.UserGender
     */
    private Byte gender;

    /**
     * qq
     * 数据库类型：varchar(16)
     */
    private String qq;

    /**
     * 固话
     * 数据库类型：varchar(16)
     */
    private String telephone;

    /**
     * 手机号码
     * 数据库类型：varchar(16)
     */
    private String mobilePhone;

    /**
     * 电子邮件
     * 数据库类型：varchar(32)
     */
    private String email;

    /**
     * 生日
     * 数据库类型：bigint
     */
    private Long birthday;

    /**
     * 备注
     * 数据库类型：varchar(4096)
     */
    private String remark;

    public static final String USERID = "userId";

    public static final String REALNAME = "realName";

    public static final String GENDER = "gender";

    public static final String QQ = "qq";

    public static final String TELEPHONE = "telephone";

    public static final String MOBILEPHONE = "mobilePhone";

    public static final String EMAIL = "email";

    public static final String BIRTHDAY = "birthday";

    public static final String REMARK = "remark";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
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
                .append(",\"userId\":\"")
                .append(userId).append('\"')
                .append(",\"realName\":\"")
                .append(realName).append('\"')
                .append(",\"gender\":")
                .append(gender)
                .append(",\"qq\":\"")
                .append(qq).append('\"')
                .append(",\"telephone\":\"")
                .append(telephone).append('\"')
                .append(",\"mobilePhone\":\"")
                .append(mobilePhone).append('\"')
                .append(",\"email\":\"")
                .append(email).append('\"')
                .append(",\"birthday\":")
                .append(birthday)
                .append(",\"remark\":\"")
                .append(remark).append('\"')
                .append('}')
                .toString();
    }
}