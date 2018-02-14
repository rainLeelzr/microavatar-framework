package microavatar.framework.core.net.tcp.netpackage.item;

import lombok.*;
import microavatar.framework.core.net.tcp.netpackage.ItemTypeEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ByteArrayItem implements Item {

    /**
     * 选项类型
     */
    private static final ItemTypeEnum ITEM_TYPE_ENUM = ItemTypeEnum.BYTE_ARRAY;

    /**
     * 选项名称
     */
    private String name;

    /**
     * 选项数据
     */
    private byte[] data;

    public static ByteArrayItem emptyItem(String name) {
        return new ByteArrayItemBuilder()
                .name(name)
                .build();
    }

    @Override
    public ItemTypeEnum getItemTypeEnum() {
        return ITEM_TYPE_ENUM;
    }

}