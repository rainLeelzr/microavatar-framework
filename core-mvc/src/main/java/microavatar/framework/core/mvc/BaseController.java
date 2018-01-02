package microavatar.framework.core.mvc;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author rain
 */
public abstract class BaseController<
        C extends BaseCriteria,
        D extends BaseDao<C, E>,
        E extends BaseEntity,
        S extends BaseService<C, D, E>> {

    protected abstract S getService();

    @PostMapping("")
    public int add(@RequestBody @Valid E entity) {
        return getService().add(entity);
    }

    @PostMapping("/batch")
    public int batchAdd(@RequestBody ArrayList<E> entitys) {
        return getService().batchAdd(entitys);
    }

    @DeleteMapping("/hard/{id}")
    public int hardDeleteById(@PathVariable Long id) {
        return getService().hardDeleteById(id);
    }

    @DeleteMapping("/hard/batch")
    public int hardDeleteByIds(@RequestBody HashSet<Long> ids) {
        return getService().hardDeleteByIds(ids);
    }

    @DeleteMapping("/{id}")
    public int softDeleteById(@PathVariable Long id) {
        return getService().softDeleteById(id);
    }

    @DeleteMapping("/batch")
    public int softDeleteByIds(@RequestBody HashSet<Long> ids) {
        return getService().softDeleteByIds(ids);
    }

    @PutMapping("/allColumns")
    public int updateAllColumnsById(@RequestBody @Valid E entity) {
        return getService().updateAllColumnsById(entity);
    }

    @PutMapping("")
    public int updateExcludeNullFieldsById(@RequestBody @Valid E entity) {
        return getService().updateExcludeNullFieldsById(entity);
    }

    @PutMapping("/allColumns/criteria")
    public int updateAllColumnsByCriteria(@Valid E entity, C criteria) {
        return getService().updateAllColumnsByCriteria(entity, criteria);
    }

    @PutMapping("/criteria")
    public int updateExcludeNullFieldsByCriteria(@RequestBody @Valid E entity, @RequestBody C criteria) {
        return getService().updateExcludeNullFieldsByCriteria(entity, criteria);
    }

    @GetMapping("/{id}")
    public E getById(@PathVariable Long id) {
        return getService().getById(id);
    }

    @PostMapping("/criteria")
    public List<E> findByCriteria(@RequestBody C criteria) {
        return getService().findByCriteria(criteria);
    }

    @PostMapping("/count/criteria")
    public long countByCriteria(@RequestBody C criteria) {
        return getService().countByCriteria(criteria);
    }

    @GetMapping("/all")
    public List<E> findAll() {
        return getService().findAll();
    }

    @GetMapping("/count/all")
    public long countAll() {
        return getService().countAll();
    }
}
