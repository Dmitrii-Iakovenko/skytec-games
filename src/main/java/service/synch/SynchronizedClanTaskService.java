package service.synch;

import model.ClanTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EntityRepository;
import service.ClanTaskService;
import util.CacheUtility;

import java.util.Optional;

public class SynchronizedClanTaskService implements ClanTaskService {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedClanTaskService.class);
    private final SynchronizedClanService clanService;
    private final CacheUtility<ClanTask> cacheUtility;
    private final EntityRepository<ClanTask> repository;

    public SynchronizedClanTaskService(SynchronizedClanService clanService, CacheUtility<ClanTask> cacheUtility, EntityRepository<ClanTask> repository) {
        this.clanService = clanService;
        this.repository = repository;
        this.cacheUtility = new CacheUtility<>(repository, ClanTask.class);
    }

    @Override
    public Optional<ClanTask> getClanTask(long clanTaskId) {
        return cacheUtility.getEntity(clanTaskId);
    }

    @Override
    public boolean completeTask(long clanId, long clanTaskId) {
        // if (success)
        Optional<ClanTask> clanTaskOptional = getClanTask(clanTaskId);
        if (clanTaskOptional.isEmpty()) {
            logger.error("Error adding gold to clan. ClanTask not found. ClanTask: {}", clanTaskId);
            return false;
        }

        int reward = clanTaskOptional.get().getReward();
        if (clanService.changeClanGold(clanId, reward)) {
            logger.info("Gold added to Clan ID: {}, ClanTask ID: {}, Reward: {}", clanId, clanTaskId, reward);
            return true;
        } else {
            logger.error("Error adding gold to clan. Clan ID: {}, ClanTask ID: {}, Reward: {}", clanId, clanTaskId, reward);
            return false;
        }
    }

}
