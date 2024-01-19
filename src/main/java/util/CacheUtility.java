package util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EntityRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class CacheUtility<T> {
    private final static Logger logger = LoggerFactory.getLogger(CacheUtility.class);
    private final Map<Long, T> cache = new HashMap<>();
    private final EntityRepository<T> repository;
    private final Class<T> type;

    public Optional<T> getEntity(long entityId) {
        T entity = cache.get(entityId);
        if (entity != null) {
            logger.debug("Cache hit for {} with id {}", type.getSimpleName(), entityId);
            return Optional.of(entity);
        }

        synchronized (cache) {
            entity = cache.get(entityId);
            if (entity != null) {
                logger.debug("Cache hit for {} with id {} after acquiring lock", type.getSimpleName(), entityId);
                return Optional.of(entity);
            }

            logger.debug("Cache miss for {} with id {}", type.getSimpleName(), entityId);
            Optional<T> entityOptional = repository.get(entityId);
            if (entityOptional.isPresent()) {
                entity = entityOptional.get();
                cache.put(entityId, entity);
                logger.debug("Entity {} with id {} loaded from repository and added to cache", type.getSimpleName(), entityId);
                return Optional.of(entity);
            } else {
                logger.error("Entity {} with id {} not found in repository", type.getSimpleName(), entityId);
                return Optional.empty();
            }
        }
    }
}
