package microavatar.framework;

import microavatar.framework.common.BaseDao;
import microavatar.framework.common.BaseEntity;
import microavatar.framework.common.BaseService;
import microavatar.framework.core.database.SqlCondition;
import microavatar.framework.core.util.log.LogUtil;
import microavatar.framework.core.util.pk.PkGenerator;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class BaseServiceTestClass<E extends BaseEntity, D extends BaseDao<E>, S extends BaseService<E, D>>
        extends AbstractTransactionalJUnit4SpringContextTests {

    protected abstract S getService();

    public abstract E genEntity();

    @Test
    public void testAdd() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().save(entity));
    }

    @Test
    public void testBatchAdd() {
        int testBatchCount = 10;
        List<E> entitys = new ArrayList<>(testBatchCount);
        for (int i = 0; i < testBatchCount; i++) {
            entitys.add(genEntity());
        }
        Assert.assertEquals(testBatchCount, getService().batchSave(entitys));
    }

    @Test
    public void testDeleteById() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().save(entity));

        Assert.assertEquals(1, getService().deleteById(entity.getId()));
    }

    @Test
    public void testDeleteByIds() {
        int testBatchCount = 10;
        List<E> entitys = new ArrayList<>(testBatchCount);
        for (int i = 0; i < testBatchCount; i++) {
            entitys.add(genEntity());
        }
        Assert.assertEquals(testBatchCount, getService().batchSave(entitys));

        Set<String> ids = new HashSet<>();
        entitys.forEach(e -> ids.add(e.getId()));
        Assert.assertEquals(ids.size(), getService().deleteByIds(ids));
    }

    @Test
    public void testUpdate() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().save(entity));

        E newEntity = genEntity();
        newEntity.setId(entity.getId());
        Assert.assertEquals(1, getService().update(newEntity));
    }

    @Test
    public void testGetById() {
        E entity = genEntity();
        LogUtil.getLogger().debug("testGetById,entity:{}", JSON.toJSONString(entity));
        entity.setId(PkGenerator.getPk());
        Assert.assertEquals(1, getService().save(entity));
        // Assert.assertEquals(entity.getId(), service.getById(entity.getId()).getId());
        // Assert.assertEquals(entity.getId(), service.getById(entity.getId()).getId());
        getService().getById(entity.getId());
        getService().getById(entity.getId());
    }

    @Test
    public void testFindPage() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().save(entity));

        PageHelper.startPage(1, 20);
        List<E> page = getService().findPage(
                new SqlCondition()
                        .select(BaseEntity.ID)
                        .where(BaseEntity.ID, entity.getId())
                        .build()
        );
        Assert.assertEquals(1, page.size());
    }

    @Test
    public void testCount() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().save(entity));

        long count = getService().count(
                new SqlCondition()
                        .select(BaseEntity.ID)
                        .where(BaseEntity.ID, entity.getId())
                        .build()
        );
        Assert.assertEquals(1, count);
    }

    @Test
    public void testCountAll() {
        long countAll = getService().countAll();
        Assert.assertThat("数据库的记录数量必须大于等于0", countAll, new Matcher<Long>() {
            @Override
            public boolean matches(Object item) {
                return ((Long) item) >= 0;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        });
    }

}
