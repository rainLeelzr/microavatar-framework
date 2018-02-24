package microavatar.framework.core.net.tcp.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import microavatar.framework.core.net.tcp.netpackage.Package;
import microavatar.framework.core.net.tcp.netpackage.item.*;

/**
 * 发送数据给客户端的时，执行此类进行编码
 */
@Slf4j
public class AvatarEncoder extends MessageToByteEncoder<Package> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Package packageData, ByteBuf out) throws Exception {
        for (Item item : packageData.getItems()) {
            switch (item.getItemTypeEnum()) {
                case BYTE:
                    out.writeByte(((ByteItem) item).getData());
                    break;
                case SHORT:
                    out.writeShort(((ShortItem) item).getData());
                    break;
                case INT:
                    out.writeInt(((IntItem) item).getData());
                    break;
                case LONG:
                    out.writeLong(((LongItem) item).getData());
                    break;
                case FLOAT:
                    out.writeFloat(((FloatItem) item).getData());
                    break;
                case DOUBLE:
                    out.writeDouble(((DoubleItem) item).getData());
                    break;
                case CHAR:
                    out.writeChar(((CharItem) item).getData());
                    break;
                case BOOLEAN:
                    out.writeBoolean(((BooleanItem) item).isData());
                    break;
                case BYTE_ARRAY:
                    out.writeBytes(((ByteArrayItem) item).getData());
                    break;
                default:
                    log.error("未实现类型为{}的编码器！", item.getItemTypeEnum());
                    break;
            }
        }
    }
}
