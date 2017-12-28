package microavatar.framework.perm.criteria;

import lombok.*;
import microavatar.framework.core.mvc.BaseCriteria;

import java.util.Set;

@Getter
@Setter
public class UserCriteria extends BaseCriteria {

    // ********** 账号 account **********
    /**
     * 账号的值等于
     */
    private String accountEquals;

    /**
     * 账号的值不等于
     */
    private String accountNotEquals;

    /**
     * 账号的值开始于
     */
    private String accountStartWith;

    /**
     * 账号的值类似
     */
    private String accountLike;

    /**
     * 账号的值不类似
     */
    private String accountNotLike;

    /**
     * 账号的值包含在
     */
    private Set<String> accountIn;

    /**
     * 账号的值不包含在
     */
    private Set<String> accountNotIn;

    // ********** 密码 pwd **********
    /**
     * 密码的值等于
     */
    private String pwdEquals;

    /**
     * 密码的值不等于
     */
    private String pwdNotEquals;

    /**
     * 密码的值开始于
     */
    private String pwdStartWith;

    /**
     * 密码的值类似
     */
    private String pwdLike;

    /**
     * 密码的值不类似
     */
    private String pwdNotLike;

    /**
     * 密码的值包含在
     */
    private Set<String> pwdIn;

    /**
     * 密码的值不包含在
     */
    private Set<String> pwdNotIn;

    // ********** 名称 name **********
    /**
     * 名称的值等于
     */
    private String nameEquals;

    /**
     * 名称的值不等于
     */
    private String nameNotEquals;

    /**
     * 名称的值开始于
     */
    private String nameStartWith;

    /**
     * 名称的值类似
     */
    private String nameLike;

    /**
     * 名称的值不类似
     */
    private String nameNotLike;

    /**
     * 名称的值包含在
     */
    private Set<String> nameIn;

    /**
     * 名称的值不包含在
     */
    private Set<String> nameNotIn;

    // ********** 状态 status **********
    /**
     * 状态的值的值小于
     */
    private Byte statusLessThan;

    /**
     * 状态的值的值小于等于
     */
    private Byte statusLessThanEquals;

    /**
     * 状态的值的值等于
     */
    private Byte statusEquals;

    /**
     * 状态的值的值大于等于
     */
    private Byte statusGreaterThanEquals;

    /**
     * 状态的值的值大于
     */
    private Byte statusGreaterThan;

    /**
     * 状态的值的值不等于
     */
    private Byte statusNotEquals;

    /**
     * 状态的值的值包含在
     */
    private Set<Byte> statusIn;

    /**
     * 状态的值的值不包含在
     */
    private Set<Byte> statusNotIn;

    /**
     * 状态的值的值介于开始
     * mysql between是包含边界值
     */
    private Byte statusBetweenStart;

    /**
     * 状态的值的值介于结束
     * mysql between是包含边界值
     */
    private Byte statusBetweenEnd;

    // ********** Constructor **********
    public UserCriteria() {
    }

    @Builder
    public UserCriteria(@Singular("selectColumns") Set<String> selectColumns,
                        Long idLessThan, Long idLessThanEquals, Long idEquals, Long idGreaterThanEquals, Long idGreaterThan, Long idNotEquals, @Singular("idIn") Set<Long> idIn, @Singular("idNotIn") Set<Long> idNotIn, Long idBetweenStart, Long idBetweenEnd,
                        Long createTimeLessThan, Long createTimeLessThanEquals, Long createTimeEquals, Long createTimeGreaterThanEquals, Long createTimeGreaterThan, Long createTimeNotEquals, @Singular("createTimeIn") Set<Long> createTimeIn, @Singular("createTimeNotIn") Set<Long> createTimeNotIn, Long createTimeBetweenStart, Long createTimeBetweenEnd,
                        Long modifyTimeLessThan, Long modifyTimeLessThanEquals, Long modifyTimeEquals, Long modifyTimeGreaterThanEquals, Long modifyTimeGreaterThan, Long modifyTimeNotEquals, @Singular("modifyTimeIn") Set<Long> modifyTimeIn, @Singular("modifyTimeNotIn") Set<Long> modifyTimeNotIn, Long modifyTimeBetweenStart, Long modifyTimeBetweenEnd,
                        Boolean deletedEquals, int pageNum, int pageSize, String orderBy,
                        String accountEquals, String accountNotEquals, String accountStartWith, String accountLike, String accountNotLike, @Singular("accountIn") Set<String> accountIn, @Singular("accountNotIn") Set<String> accountNotIn,
                        String pwdEquals, String pwdNotEquals, String pwdStartWith, String pwdLike, String pwdNotLike, @Singular("pwdIn") Set<String> pwdIn, @Singular("pwdNotIn") Set<String> pwdNotIn,
                        String nameEquals, String nameNotEquals, String nameStartWith, String nameLike, String nameNotLike, @Singular("nameIn") Set<String> nameIn, @Singular("nameNotIn") Set<String> nameNotIn,
                        Byte statusLessThan, Byte statusLessThanEquals, Byte statusEquals, Byte statusGreaterThanEquals, Byte statusGreaterThan, Byte statusNotEquals, @Singular("statusIn") Set<Byte> statusIn, @Singular("statusNotIn") Set<Byte> statusNotIn, Byte statusBetweenStart, Byte statusBetweenEnd
    ) {
        super(selectColumns,
                idLessThan, idLessThanEquals, idEquals, idGreaterThanEquals, idGreaterThan, idNotEquals, idIn, idNotIn, idBetweenStart, idBetweenEnd,
                createTimeLessThan, createTimeLessThanEquals, createTimeEquals, createTimeGreaterThanEquals, createTimeGreaterThan, createTimeNotEquals, createTimeIn, createTimeNotIn, createTimeBetweenStart, createTimeBetweenEnd,
                modifyTimeLessThan, modifyTimeLessThanEquals, modifyTimeEquals, modifyTimeGreaterThanEquals, modifyTimeGreaterThan, modifyTimeNotEquals, modifyTimeIn, modifyTimeNotIn, modifyTimeBetweenStart, modifyTimeBetweenEnd,
                deletedEquals, pageNum, pageSize, orderBy);
        this.accountEquals = accountEquals;
        this.accountNotEquals = accountNotEquals;
        this.accountStartWith = accountStartWith;
        this.accountLike = accountLike;
        this.accountNotLike = accountNotLike;
        this.accountIn = accountIn;
        this.accountNotIn = accountNotIn;
        this.pwdEquals = pwdEquals;
        this.pwdNotEquals = pwdNotEquals;
        this.pwdStartWith = pwdStartWith;
        this.pwdLike = pwdLike;
        this.pwdNotLike = pwdNotLike;
        this.pwdIn = pwdIn;
        this.pwdNotIn = pwdNotIn;
        this.nameEquals = nameEquals;
        this.nameNotEquals = nameNotEquals;
        this.nameStartWith = nameStartWith;
        this.nameLike = nameLike;
        this.nameNotLike = nameNotLike;
        this.nameIn = nameIn;
        this.nameNotIn = nameNotIn;
        this.statusLessThan = statusLessThan;
        this.statusLessThanEquals = statusLessThanEquals;
        this.statusEquals = statusEquals;
        this.statusGreaterThanEquals = statusGreaterThanEquals;
        this.statusGreaterThan = statusGreaterThan;
        this.statusNotEquals = statusNotEquals;
        this.statusIn = statusIn;
        this.statusNotIn = statusNotIn;
        this.statusBetweenStart = statusBetweenStart;
        this.statusBetweenEnd = statusBetweenEnd;
    }
}
