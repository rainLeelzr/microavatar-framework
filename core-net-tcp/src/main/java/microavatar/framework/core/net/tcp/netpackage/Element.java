package microavatar.framework.core.net.tcp.netpackage;

public class Element {

    /**
     * 元素名称
     */
    private String name;

    private ElementEnum elementEnum;

    public Element(String name, ElementEnum elementEnum) {
        this.name = name;
        this.elementEnum = elementEnum;
    }

    public String getName() {
        return name;
    }

    public ElementEnum getElementEnum() {
        return elementEnum;
    }
}
