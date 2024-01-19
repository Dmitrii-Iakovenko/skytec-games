package repository;

import java.util.Optional;

public interface EntityRepository<T> {
    Optional<T> get(long entityId);
    boolean save(T entity);
}
