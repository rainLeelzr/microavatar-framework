package microavatar.framework.core.net.tcp.netpackage;

/**
 * 报文包
 */
public interface Package {

    /**
     * 报文包的版本
     */
    int getVersion();

    /**
     * 获取整个报文包的长度
     */
    int getFullLength();

    /**
     * 报文包的body部分的长度
     */
    int getBodyLength();

    /**
     * 获取报文的所有元素，元素顺序是真实报文包的数据顺序
     */
    Element[] getElements();
}
