package microavatar.framework.core.net.tcp.netpackage.item;

import lombok.*;
import microavatar.framework.core.net.tcp.netpackage.ItemTypeEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloatItem implements Item {

    /**
     * 选项类型
     */
    private static final ItemTypeEnum ITEM_TYPE_ENUM = ItemTypeEnum.FLOAT;

    /**
     * 选项名称
     */
    private String name;

    /**
     * 选项数据
     */
    private float data;

    public static FloatItem emptyItem(String name) {
        return new FloatItemBuilder()
                .name(name)
                .build();
    }


    @Override
    public ItemTypeEnum getItemTypeEnum() {
        return ITEM_TYPE_ENUM;
    }

}