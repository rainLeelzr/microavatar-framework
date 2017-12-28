```
-Xms64M -Xmx256M -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128M -Dspring.profiles.active=dev1
```

首先我们看bigint, MySQL的整型类型有这样几种：
类型      占用字节
tinyint        1
smallint     2
mediumint 3
int              4
bigint         8
这是决定存储需要占用多少字节，那么后边的数字(M)代表什么意思呢
tinyint(M), M默认为4;
SMALLINT(M), M默认为6;
MEDIUMINT(M), M默认为9;
INT(M),M默认为11;
BIGINT(M),M默认为20.