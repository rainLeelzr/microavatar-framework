package microavatar.framework.core.net.tcp.netpackage;

import microavatar.framework.core.net.tcp.netpackage.item.*;

public class HttpPackage implements Package {

    public static final String FULL_LENGTH = "fullLength";
    public static final String VERSION = "version";
    public static final String URL_LENGTH = "urlLength";
    public static final String URL = "url";
    public static final String HTTP_METHOD = "httpMethod";
    public static final String BODY_TYPE = "bodyType";
    public static final String BODY_LENGTH = "bodyLength";
    public static final String BODY = "body";

    public static final String URL_CHART_SET = "UTF-8";

    private static final Item[] PACKAGE_STRUCTURE = new Item[]{
            IntItem.emptyItem(FULL_LENGTH),
            ShortItem.emptyItem(VERSION),
            IntItem.emptyItem(URL_LENGTH),
            ByteArrayItem.emptyItem(URL),
            ByteItem.emptyItem(HTTP_METHOD),
            ByteItem.emptyItem(BODY_TYPE),
            IntItem.emptyItem(BODY_LENGTH),
            ByteArrayItem.emptyItem(BODY),
    };

    /**
     * 储存真实的报文数据
     */
    private Item[] items;

    @Override
    public Item[] initItems() {
        this.items = new Item[PACKAGE_STRUCTURE.length];
        for (int i = 0; i < PACKAGE_STRUCTURE.length; i++) {
            Item item = PACKAGE_STRUCTURE[i];
            try {
                Item newItem = item.getClass().newInstance();
                newItem.setName(item.getName());
                items[i] = newItem;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.items;
    }

    @Override
    @SuppressWarnings("unchecked")
    public short getVersion() {
        ShortItem item = (ShortItem) getItem(VERSION);
        return item.getData();
    }

    @Override
    public int getFullLength() {
        return 0;
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public Item[] getItems() {
        if (items == null) {
            throw new RuntimeException("items未初始化，请先初始化！");
        }
        return items;
    }

    @Override
    public String getPackageStructure() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PACKAGE_STRUCTURE.length; i++) {
            Item item = PACKAGE_STRUCTURE[i];
            sb.append("第").append(i + 1).append("个字段是")
                    .append(item.getName())
                    .append("，")
                    .append(item.getItemTypeEnum())
                    .append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new HttpPackage().getPackageStructure());
        System.out.println(HttpPackage.PACKAGE_STRUCTURE);
        System.out.println(new HttpPackage());
        System.out.println(new HttpPackage().initItems());
    }

    public enum HttpMethodEnum {

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

        HttpMethodEnum(byte id) {
            this.id = id;
        }

        public byte geId() {
            return id;
        }
    }

}
