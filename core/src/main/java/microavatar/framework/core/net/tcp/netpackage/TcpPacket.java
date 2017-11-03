package microavatar.framework.core.net.tcp.netpackage;

import microavatar.framework.common.util.log.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

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
public class TcpPacket {

    /**
     * 一个完整的tcp包最少的长度（字节）
     */
    public static final int MIN_LENGTH = 10;

    /**
     * 一个完整的tcp包最大的长度（字节）≈102M
     */
    public static final int MAX_LENGTH = 106954762;

    /**
     * urlLength最大的取值 1M
     */
    public static final int MAX_URL_LENGTH = 1024 * 1024;

    /**
     * 默认的编码解码字符串的字符集
     */
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * 整个包的长度（字节）
     */
    private int length;

    /**
     * 请求方法（参照http method的取值）
     */
    private byte method;

    /**
     * url的长度（字节）
     */
    private int urlLength;

    /**
     * 请求的url
     */
    private byte[] url;

    /**
     * body数据的格式
     */
    private byte bodyType;

    /**
     * body数据
     */
    private byte[] body;


    /**
     * tcp包的method的取值
     */
    public enum MethodEnum {

        // 从指定的url上获取内容
        GET((byte) 1),

        /*
            HEAD方法与GET方法的行为很类似，但服务器在响应中只返回首部。不会反回实体的主体部分。这就允许客户端在未获取实际资源的情况下，对资源的首部进行检查。
            使用HEAD，可以：
            在不获取资源的情况下，了解资源的情况
            通过查看响应中的状态码，看看某个对象是否存在
            通过查看首部，测试资源是否被修改
            服务器开发者必须确保返回的首部与GET请求返回的首部完全相同
         */
        HEAD((byte) 2),

        // 与GET方法从服务器读取文档相反，PUT方法会向服务器写入文档。有些发布系统允许用户创建WEB页面，并用PUT直接将其安装到WEB服务器上。
        PUT((byte) 3),

        // POST方法起初是用来向服务器写入数据的。实际上，通常会用它来支持HTML的表单。表单中填好的数据通常会被发送给服务器，然后服务器将其发送到他要去的地方。
        POST((byte) 4),

        /*
            TRACE方法允许客户端在最终将请求发送给服务器时，看看他变成了什么样子。
            TRACE请求最终会在目的服务器发起一个回环诊断，行程最后一站的服务器会弹回一条TRACE响应，并在响应主体中携带它收到的原始请求报文。这样客户端就可以查看在所有中间HTTP程序组成的请求响应链上，原始报文是否以及如何被毁坏或修改过。
            TRACE方法主要用于诊断
            中间应用程序会自行决定对TRACE请求的处理方式
            TRACE请求不能带有实体的主体部分。TRACE响应的实体主体部分包含了响应服务器收到的请求的精确副本。
         */
        TRACE((byte) 5),

        // OPTIONS方法请求WEB服务器告知其支持的各种功能。可以询问服务器通常支持哪些方法，或者对某些特殊资源支持哪些方法。
        OPTIONS((byte) 6),

        /*
            DELETE方法所做的事情就是请服务器删除请求URL所指定的资源。
            但是客户端应用程序无法保证删除操作一定会执行。因为HTTP规范允许服务器在不通知客户端的情况下撤销请求。
        */
        DELETE((byte) 7),

        PATCH((byte) 8);

        private byte id;

        MethodEnum(byte id) {
            this.id = id;
        }

        public byte geId() {
            return id;
        }
    }

    /**
     * tcp包的body部分的数据类型
     */
    public enum BodyTypeEnum {

        PROTOBUF((byte) 0),
        JSON((byte) 1);

        private byte id;

        BodyTypeEnum(byte id) {
            this.id = id;
        }

        public byte geId() {
            return id;
        }
    }

    public TcpPacket(int length, byte method, int urlLength, byte[] url, byte bodyType, byte[] body) {
        this.length = length;
        this.method = method;
        this.urlLength = urlLength;
        this.url = url;
        this.bodyType = bodyType;
        this.body = body;
    }

    /**
     * 构建body类型为proto格式的tcp包
     *
     * @param url  url
     * @param body body的二进制数组数据
     */
    public static TcpPacket buildProtoPackage(MethodEnum methodEnum, String url, byte[] body) {
        return buildPackage(methodEnum, url, BodyTypeEnum.PROTOBUF, body);
    }

    public static TcpPacket buildProtoPackage(byte method, String url, byte[] body) {
        return buildPackage(method, url, BodyTypeEnum.PROTOBUF, body);
    }

    /**
     * 构建body类型为Json格式的tcp包
     *
     * @param url  url
     * @param body body的二进制数组数据
     */
    public static TcpPacket buildJsonPackage(MethodEnum methodEnum, String url, byte[] body) {
        return buildPackage(methodEnum, url, BodyTypeEnum.JSON, body);
    }

    public static TcpPacket buildJsonPackage(byte method, String url, byte[] body) {
        return buildPackage(method, url, BodyTypeEnum.JSON, body);
    }

    /**
     * 构建body类型为Json格式的tcp包
     *
     * @param url  url
     * @param body body的字符串数据
     */
    public static TcpPacket buildJsonPackage(MethodEnum methodEnum, String url, String body) {
        byte[] bodyBytes;
        try {
            bodyBytes = body.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            bodyBytes = new byte[0];
        }
        return buildJsonPackage(methodEnum, url, bodyBytes);
    }

    public static TcpPacket buildJsonPackage(byte method, String url, String body) {
        byte[] bodyBytes;
        try {
            bodyBytes = body.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            bodyBytes = new byte[0];
        }
        return buildJsonPackage(method, url, bodyBytes);
    }


    /**
     * 构建tcp包
     *
     * @param url          url
     * @param bodyTypeEnum body数据的格式
     * @param body         body数据
     */
    public static TcpPacket buildPackage(MethodEnum methodEnum, String url, BodyTypeEnum bodyTypeEnum, byte[] body) {
        return buildPackage(methodEnum.id, url, bodyTypeEnum, body);
    }

    public static TcpPacket buildPackage(byte method, String url, BodyTypeEnum bodyTypeEnum, byte[] body) {
        byte[] urlBytes;
        try {
            urlBytes = url.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            LogUtil.getLogger().error(e.getMessage(), e);
            urlBytes = new byte[0];
        }
        int length = MIN_LENGTH + urlBytes.length + body.length;
        return new TcpPacket(length, method, urlBytes.length, urlBytes, bodyTypeEnum.id, body);
    }

    /**
     * 服务器发送给客户端的数据包的格式
     */
    public ByteBuf getByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeInt(this.length);
        byteBuf.writeByte(this.method);
        byteBuf.writeInt(this.urlLength);
        byteBuf.writeBytes(this.url);
        byteBuf.writeByte(this.bodyType);
        byteBuf.writeBytes(this.body);
        return byteBuf;
    }

    public byte getBodyType() {
        return bodyType;
    }

    public byte[] getBody() {
        return body;
    }

    public byte getMethod() {
        return method;
    }

    /**
     * url的字符串形式
     */
    private String urlStr;

    public String getUrlStr() {
        if (this.url == null) {
            return null;
        }

        if (this.urlStr == null) {
            try {
                urlStr = new String(this.url, DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                LogUtil.getLogger().error(e.getMessage(), e);
                urlStr = "";
            }
        }

        return this.urlStr;
    }

    /**
     * body的字符串形式
     */
    private String bodyStr;

    public String getBodyStr() {
        if (this.body == null) {
            return null;
        }

        if (this.bodyStr == null) {
            try {
                bodyStr = new String(this.body, DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                LogUtil.getLogger().error(e.getMessage(), e);
                bodyStr = "";
            }
        }

        return this.bodyStr;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"length\":")
                .append(length)
                .append(",\"method\":")
                .append(method)
                .append(",\"urlLength\":")
                .append(urlLength)
                .append(",\"bodyType\":")
                .append(bodyType)
                .append(",\"urlStr\":\"")
                .append(getUrlStr()).append('\"')
                .append('}')
                .toString();
    }
}
