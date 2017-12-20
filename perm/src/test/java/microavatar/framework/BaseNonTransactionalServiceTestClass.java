package microavatar.framework;

import microavatar.framework.core.mvc.BaseDao;
import microavatar.framework.core.mvc.BaseEntity;
import microavatar.framework.core.mvc.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class BaseNonTransactionalServiceTestClass<E extends BaseEntity, D extends BaseDao<E>, S extends BaseService<E, D>>
        extends AbstractJUnit4SpringContextTests implements ServiceTest {

    @Test
    public void testAdd() {
        add();
    }

    @Test
    public void testBatchAdd() {
        batchAdd();
    }

    @Test
    public void testDeleteById() {
        deleteById();
    }

    @Test
    public void testDeleteByIds() {
        deleteByIds();
    }

    @Test
    public void testUpdate() {
        update();
    }

    @Test
    public void testGetById() {
        getById();
    }

    @Test
    public void testFindPage() {
        findPage();
    }

    @Test
    public void testCount() {
        count();
    }

    @Test
    public void testCountAll() {
        countAll();
    }

}
