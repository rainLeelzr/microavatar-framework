package microavatar.framework.core.net.tcp.netpackage.item;

import microavatar.framework.core.net.tcp.netpackage.ItemTypeEnum;

public interface Item {

    void setName(String name);

    /**
     * 选项名称
     */
    String getName();

    /**
     * 选项类型
     */
    ItemTypeEnum getItemTypeEnum();

}
