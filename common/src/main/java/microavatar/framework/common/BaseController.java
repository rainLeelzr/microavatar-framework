package microavatar.framework.common;

public abstract class BaseController<S extends BaseService<E, D>, D extends BaseDao<E>, E extends BaseEntity> {

    protected abstract S getService();

    public E getEntityById(String id) {
        return getService().getById(id);
    }
}
