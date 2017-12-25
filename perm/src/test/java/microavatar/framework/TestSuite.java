package microavatar.framework;

import microavatar.framework.perm.PermTestSuite;
import microavatar.framework.base.BaseTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PermTestSuite.class,
        BaseTestSuite.class
})
public class TestSuite {
}
