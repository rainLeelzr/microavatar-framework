package microavatar.framework;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import microavatar.framework.core.mvc.BaseCriteria;
import microavatar.framework.core.mvc.BaseDao;
import microavatar.framework.core.mvc.BaseEntity;
import microavatar.framework.core.mvc.BaseService;
import microavatar.framework.core.support.sequence.impl.LongSequence;
import microavatar.framework.perm.entity.User;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ServiceTest<
        C extends BaseCriteria,
        D extends BaseDao<C, E>,
        E extends BaseEntity<E>,
        S extends BaseService<C, D, E>> {

    C getCriteria();

    E genEntity();

    S getService();

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

        Assert.assertEquals(1, getService().hardDeleteById(entity.getId()));
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
        Assert.assertEquals(ids.size(), getService().hardDeleteByIds(ids));
    }

    default void update() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        E newEntity = genEntity();
        newEntity.setId(entity.getId());
        Assert.assertEquals(1, getService().updateAllColumnsById(newEntity));
    }

    default void getById() {
        E entity = genEntity();
        entity.setId(LongSequence.get());
        Assert.assertEquals(1, getService().add(entity));

        E e = getService().getById(entity.getId());
        Assert.assertEquals(entity.getId(), e.getId());
    }

    default void findByCriteria() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        C criteria = getCriteria();
        criteria.setIdEquals(entity.getId());
        criteria.setPageNum(1);
        criteria.setPageSize(1);
        criteria.setCreateTimeGreaterThanEquals(33L);

        HashSet<String> selectColumns = new HashSet<>(2);
        selectColumns.add(E.ID);
        selectColumns.add(E.CREATE_TIME);
        selectColumns.add(E.DELETED);
        selectColumns.add(User.ACCOUNT);
        criteria.setSelectColumns(selectColumns);

        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        PageHelper.orderBy(criteria.getOrderBy());

        List<E> page = getService().findByCriteria(criteria);
        PageInfo<E> pageInfo = new PageInfo<>(page);

        Assert.assertEquals(1, page.size());
    }

    default void countByCriteria() {
        E entity = genEntity();
        Assert.assertEquals(1, getService().add(entity));

        C criteria = getCriteria();
        criteria.setIdEquals(entity.getId());
        criteria.setPageNum(1);
        criteria.setPageSize(1);

        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        long count = getService().countByCriteria(criteria);
        Assert.assertEquals(1, count);
    }

    default void findAll() {
        getService().findAll();
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
