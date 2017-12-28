package microavatar.framework.core.mvc;

/**
 * @author rain
 */
public abstract class BaseController<
        C extends BaseCriteria,
        D extends BaseDao<C, E>,
        E extends BaseEntity,
        S extends BaseService<C, D, E>> {

    protected abstract S getService();

    public E getById(Long id) {
        return getService().getById(id);
    }
}
