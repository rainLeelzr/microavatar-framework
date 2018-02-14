package microavatar.framework.core.net.tcp.netpackage;

import microavatar.framework.core.net.tcp.netpackage.item.Item;

/**
 * 报文包
 */
public interface Package {

    /**
     * 报文包的版本
     */
    short getVersion();

    /**
     * 获取报文包至少的长度
     */
    int getLeastLength();

    /**
     * 获取当前报文包数据的完整长度
     */
    int getFullLength();

    /**
     * 获取当前报文包的body部分的长度
     */
    int getBodyLength();

    /**
     * 初始化报文包
     */
    Item[] initItems();

    /**
     * 获取报文的所有元素，元素顺序是真实报文包的数据顺序
     */
    Item[] getItems();

    default Item getItem(String itemName) {
        if (itemName == null || itemName.length() == 0) {
            throw new RuntimeException("itemName不能为空");
        }
        Item[] items = getItems();
        if (items == null) {
            return null;
        }

        for (Item item : items) {
            if (item == null) {
                continue;
            }

            if (itemName.equalsIgnoreCase(item.getName())) {
                return item;
            }
        }

        return null;
    }

    String getPackageStructure();
}
