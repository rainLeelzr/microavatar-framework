package microavatar.framework;

import com.github.pagehelper.PageHelper;
import microavatar.framework.core.database.SqlCondition;
import microavatar.framework.core.mvc.BaseDao;
import microavatar.framework.core.mvc.BaseEntity;
import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.core.support.sequence.impl.LongSequence;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ServiceTest<E extends BaseEntity<E>, D extends BaseDao<E>, S extends BaseService<E, D>> {

    S getService();

    E genEntity();

    default void add() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));
    }

    default void batchAdd() {
        int testBatchCount = 10;
        List<E> entitys = new ArrayList<>(testBatchCount);
        for (int i = 0; i < testBatchCount; i++) {
            entitys.add(genEntity());
        }
        Assert.assertEquals(testBatchCount, getService().batchAdd(entitys));
    }

    default void deleteById() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        Assert.assertEquals(1, getService().deleteById(entity.getId()));
    }

    default void deleteByIds() {
        int testBatchCount = 10;
        List<E> entitys = new ArrayList<>(testBatchCount);
        for (int i = 0; i < testBatchCount; i++) {
            entitys.add(genEntity());
        }
        Assert.assertEquals(testBatchCount, getService().batchAdd(entitys));

        Set<Long> ids = new HashSet<>();
        entitys.forEach(e -> ids.add(e.getId()));
        Assert.assertEquals(ids.size(), getService().deleteByIds(ids));
    }

    default void update() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        E newEntity = genEntity();
        newEntity.setId(entity.getId());
        Assert.assertEquals(1, getService().update(newEntity));
    }

    default void getById() {
        E entity = genEntity();
        entity.setId(LongSequence.get());
        Assert.assertEquals(1, getService().add(entity));
        // Assert.assertEquals(entity.getId(), service.getById(entity.getId()).getId());
        // Assert.assertEquals(entity.getId(), service.getById(entity.getId()).getId());
        getService().getById(entity.getId());
        getService().getById(entity.getId());
    }

    default void findPage() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        PageHelper.startPage(1, 20);
        List<E> page = getService().findPage(
                new SqlCondition()
                        .select(BaseEntity.ID)
                        .where(BaseEntity.ID, entity.getId())
                        .build()
        );
        Assert.assertEquals(1, page.size());
    }

    default void count() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        long count = getService().count(
                new SqlCondition()
                        .select(BaseEntity.ID)
                        .where(BaseEntity.ID, entity.getId())
                        .build()
        );
        Assert.assertEquals(1, count);
    }

    default void countAll() {
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
            @SuppressWarnings("deprecation")
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        });
    }

}
