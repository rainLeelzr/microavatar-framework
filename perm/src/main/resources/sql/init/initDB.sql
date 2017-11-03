/*===================== Table: Auth_User =====================*/
create table Auth_User (
    id                             varchar(36) not null,
    account                        varchar(16),
    pwd                            varchar(32),
    createTime                     bigint,
    name                           varchar(16) comment '取值为第一次登录系统时所携带的昵称
例如第一次采用微信登录，则此字段赋值为微信登录的昵称
以后次登录时，该用户采用新浪账号绑定并登录，此时本字段不变。
用户的后台个人信息页面中，显示的昵称为本字段，本字段可供用户主动修改
因“昵称”为常用字段，所以放在本类，以便业务快速获取',
    status                         tinyint,
    primary key (id)
);

alter table Auth_User comment '1:不存放基本的用户信息，与业务无关。
2:用户可以采用用户名密码、微信、新浪等方式登录或绑定账号，但是user只有一条记录，因为他们是同一个用户
3:如果业务系统需要更多userInfo里没有的用户信息，需要自己定义userExt扩展表来记录用户的信息';


/*===================== Table: Auth_UserInfo =====================*/
create table Auth_UserInfo (
    id                             varchar(36) not null,
    userId                         varchar(36),
    realName                       varchar(16),
    gender                         tinyint,
    qq                             varchar(16),
    telephone                      varchar(16),
    mobilePhone                    varchar(16),
    email                          varchar(32),
    birthday                       bigint,
    remark                         varchar(4096),
    primary key (id)
);

alter table Auth_UserInfo comment '用户信息实体，存放基本的用户信息';


/*===================== Table: Auth_ThirdPartyUser =====================*/
create table Auth_ThirdPartyUser (
    id                             varchar(36) not null,
    userId                         varchar(36),
    loginType                      tinyint,
    accessTime                     bigint,
    uniqueId                       varchar(64),
    primary key (id)
);

alter table Auth_ThirdPartyUser comment '第三方系统与本系统的绑定关系';


/*===================== Table: Auth_LoginLog =====================*/
create table Auth_LoginLog (
    id                             varchar(36) not null,
    userId                         varchar(36),
    loginType                      tinyint,
    clientIp                       varchar(16),
    loginTime                      bigint,
    logoutTime                     bigint,
    status                         tinyint,
    primary key (id)
);

alter table Auth_LoginLog comment '只要有登录请求，无论登录成功与否，都记录';


/*===================== Table: Auth_SysResource =====================*/
create table Auth_SysResource (
    id                             varchar(36) not null,
    parentId                       varchar(36),
    name                           varchar(64),
    type                           tinyint,
    host                           varchar(64),
    url                            varchar(256),
    orderNum                       tinyint,
    enabled                        tinyint,
    remark                         varchar(4096),
    primary key (id)
);

alter table Auth_SysResource comment '资源，包括菜单(页面)、按钮等可以访问的资源';


/*===================== Table: Auth_Role =====================*/
create table Auth_Role (
    id                             varchar(36) not null,
    parentId                       varchar(36),
    name                           varchar(64),
    code                           varchar(64),
    orderNum                       tinyint,
    createdUserId                  varchar(36),
    enabled                        tinyint,
    remark                         varchar(4096),
    primary key (id)
);

alter table Auth_Role comment '角色';


/*===================== Table: Auth_RoleSysResource =====================*/
create table Auth_RoleSysResource (
    id                             varchar(36) not null,
    roleId                         varchar(36),
    sysResourceId                  varchar(36),
    primary key (id)
);

alter table Auth_RoleSysResource comment '角色资源关系';


/*===================== Table: Auth_UserRole =====================*/
create table Auth_UserRole (
    id                             varchar(36) not null,
    userId                         varchar(36),
    roleId                         varchar(36),
    primary key (id)
);

alter table Auth_UserRole comment '用户角色关系';


/*===================== Table: Auth_Org =====================*/
create table Auth_Org (
    id                             varchar(36) not null,
    parentId                       varchar(36),
    name                           varchar(64),
    abbreviation                   varchar(64),
    address                        varchar(256),
    contact                        varchar(256) comment '可以写email、固话、手机等一切联系方式',
    orderNum                       tinyint,
    createdUserId                  varchar(36),
    enabled                        tinyint,
    remark                         varchar(4096),
    primary key (id)
);

alter table Auth_Org comment '组织，包括集团公司、分公司、事业群、部门、小组、分队等所有团队性质的组织';


/*===================== Table: Auth_UserOrg =====================*/
create table Auth_UserOrg (
    id                             varchar(36) not null,
    userId                         varchar(36),
    orgId                          varchar(36),
    primary key (id)
);

alter table Auth_UserOrg comment '用户组织关系';


/*===================== Table: Auth_SysPermission =====================*/
create table Auth_SysPermission (
    id                             varchar(36) not null,
    sysResourceId                  varchar(36),
    name                           varchar(64),
    code                           varchar(64),
    orderNum                       tinyint,
    remark                         varchar(4096),
    primary key (id)
);

alter table Auth_SysPermission comment '系统权限，对资源的操作权限、如对用户添加、删除、修改';


/*===================== Table: Auth_RoleSysPermission =====================*/
create table Auth_RoleSysPermission (
    id                             varchar(36) not null,
    roleId                         varchar(36),
    sysPermissionId                varchar(36),
    primary key (id)
);

alter table Auth_RoleSysPermission comment '角色权限关系';


/*===================== Table: Base_Param =====================*/
create table Base_Param (
    id                             varchar(36) not null,
    name                           varchar(32) comment '参数名',
    type                           tinyint comment '参数类型。1：系统框架参数；2：业务系统参数',
    keyStr                         varchar(64) comment '参数的key',
    valueStr                       varchar(256) comment '参数的值',
    valueType                      tinyint comment '值的格式
1：字符串；2：整形；3：浮点型；4：布尔
5：字符串数组；6：整形数组；7：浮点型数组；8：布尔数组',
    remark                         varchar(256),
    primary key (id)
);

alter table Base_Param comment '参数表，存放所有系统参数和业务参数';


/*===================== Table: Base_ErrorInfo =====================*/
create table Base_ErrorInfo (
    id                             varchar(36) not null,
    keyStr                         varchar(64) comment '错误代码的key',
    code                           int comment '错误代码',
    msg                            varchar(128) comment '发生错误时，具体的错误信息',
    primary key (id)
);

alter table Base_ErrorInfo comment '错误信息表，存放系统所有的自定义错误信息，系统初始化时，会加载全部信息到内存';