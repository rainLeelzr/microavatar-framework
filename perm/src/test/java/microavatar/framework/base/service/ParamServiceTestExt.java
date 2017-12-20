package microavatar.framework.base.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.annotation.Resource;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParamServiceTestExt extends ParamServiceTest {

    @Resource(name = "paramServiceExt")
    private ParamServiceExt paramServiceExt;

    @Test
    public void TestGetByParamKey() {
        String value1 = paramServiceExt.getParamObjectByKey("1", String.class);
        System.out.println(value1);
        Integer value2 = paramServiceExt.getParamObjectByKey("base:param:type~biz", Integer.class);
        System.out.println(value2);
        Float value3 = paramServiceExt.getParamObjectByKey("3", Float.class);
        System.out.println(value3);
        Boolean value4 = paramServiceExt.getParamObjectByKey("4", Boolean.class);
        System.out.println(value4);
        List<String> value5 = paramServiceExt.getParamArrayByKey("5", String.class);
        System.out.println(value5);
        List<Integer> value6 = paramServiceExt.getParamArrayByKey("6", Integer.class);
        System.out.println(value6);
        List<Float> value7 = paramServiceExt.getParamArrayByKey("7", Float.class);
        System.out.println(value7);
        List<Boolean> value8 = paramServiceExt.getParamArrayByKey("8", Boolean.class);
        System.out.println(value8);
    }


}