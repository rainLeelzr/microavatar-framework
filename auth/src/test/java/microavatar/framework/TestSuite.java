package microavatar.framework;

import microavatar.framework.auth.AuthTestSuite;
import microavatar.framework.base.BaseTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthTestSuite.class,
        BaseTestSuite.class
})
public class TestSuite {
}
