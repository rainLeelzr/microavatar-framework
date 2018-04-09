package microavatar.framework.core.net.tcp.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.netpackage.impl.BluePackage;
import microavatar.framework.core.net.tcp.netpackage.ItemTypeEnum;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.netpackage.item.*;

import java.util.List;

/**
 * 收到客户端的数据后，执行此类进行数据解码
 *
 * @author Rain
 */
@Slf4j
public class AvatarDecoder extends ByteToMessageDecoder {

    /**
     * tcp数据包的报文结构
     * ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┐
     * ╎                              header                            ╎    body    ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎   length   ╎   method   ╎  urlLength ╎     url    ╎  bodyType  ╎    body    ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎     4B     ╎     1B     ╎     4B     ╎    0~1M    ╎     1B     ╎   0~100M   ╎
     * ├╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌┤
     * ╎                            10B~106954762B(≈102M)                            ╎
     * └╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int readableBytes = in.readableBytes();

        log.debug("收到网络包，长度：{}", readableBytes);

        // 获取网络包的前4个字节，作为一个完整报文包的数据长度
        if (readableBytes < Integer.BYTES) {
            return;
        }

        in.markReaderIndex();

        // 整个包的长度（字节）
        int fullLength = in.readInt();
        if (fullLength < 0) {
            log.error("tcp数据包的length值为负数[{}], 将强制关闭此tcp链接！{}", fullLength, ctx);
            ctx.close();
            return;
        }

        // 整包的长度小于可读字节数，说明数据未完全接收。返回，等待下次读取
        if (fullLength < readableBytes) {
            in.resetReaderIndex();
            return;
        }

        Package packageData = createPackage(ctx, in, fullLength);
        if (packageData == null) {
            return;
        }

        out.add(packageData);
    }

    private Package createPackage(ChannelHandlerContext ctx, ByteBuf in, int fullLength) {
        Package packageData = new BluePackage();
        Item[] items = packageData.getItems();
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (i == 0) {
                ((IntItem) item).setData(fullLength);
            } else {
                ItemTypeEnum itemTypeEnum = item.getItemTypeEnum();
                switch (itemTypeEnum) {
                    case INT:
                        ((IntItem) item).setData(in.readInt());
                        break;
                    case LONG:
                        ((LongItem) item).setData(in.readLong());
                        break;
                    case FLOAT:
                        ((FloatItem) item).setData(in.readFloat());
                        break;
                    case DOUBLE:
                        ((DoubleItem) item).setData(in.readDouble());
                        break;
                    case BOOLEAN:
                        ((BooleanItem) item).setData(in.readBoolean());
                        break;
                    case BYTE_ARRAY:
                        ByteArrayItem thisItem = (ByteArrayItem) item;
                        byte[] bytes = createByteArray(items[i - 1]);
                        if (bytes == null) {
                            log.error("无法为当前字节数组获取其的长度（上一个元素的值应该是本字节数据的长度），上一个元素是{}。将强制关闭此tcp链接！{}",
                                    items[i - 1], ctx);
                            ctx.close();
                            return null;
                        }
                        in.readBytes(bytes);
                        thisItem.setData(bytes);
                        break;
                    default:
                        log.warn("未实现itemType为{}的解码，将强制关闭此tcp链接！{}", itemTypeEnum, ctx);
                        ctx.close();
                        return null;
                }
            }
        }
        return packageData;
    }

    /**
     * 获取上一个元素的值，作为当前元素的字节数组大小
     */
    private byte[] createByteArray(Item lastItem) {
        byte[] bytes = null;
        switch (lastItem.getItemTypeEnum()) {
            case INT:
                IntItem intItem = (IntItem) lastItem;
                if (intItem.getData() < 0) {
                    log.error("指定的长度[{]}少于0", intItem.getData());
                } else {
                    bytes = new byte[intItem.getData()];
                }
                break;
            case LONG:
                LongItem longItem = (LongItem) lastItem;
                if (longItem.getData() < 0) {
                    log.error("指定的长度[{]}少于0", longItem.getData());
                } else if (longItem.getData() > Integer.MAX_VALUE) {
                    log.error("指定的长度[{]}超过new byte[]的最大长度Integer.MAX_VALUE[{}]",
                            longItem.getData(), Integer.MAX_VALUE);
                } else {
                    bytes = new byte[(int) longItem.getData()];
                }
                break;
            default:
                break;
        }

        return bytes;
    }

}
