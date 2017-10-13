package microavatar.framework.base;

import microavatar.framework.base.service.ErrorInfoServiceTest;
import microavatar.framework.base.service.ParamServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ParamServiceTest.class,
        ErrorInfoServiceTest.class
})
public class BaseTestSuite {
}