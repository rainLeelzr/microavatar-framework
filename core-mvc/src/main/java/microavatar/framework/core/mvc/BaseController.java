package microavatar.framework.core.mvc;

/**
 * @author rain
 */
public abstract class BaseController<S extends BaseService<E, D>, D extends BaseDao<E>, E extends BaseEntity> {

    protected abstract S getService();

    public E getById(Long id) {
        return getService().getById(id);
    }
}
