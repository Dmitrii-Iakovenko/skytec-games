package service.synch;

import model.Clan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.EntityRepository;
import service.ClanService;
import util.CacheUtility;

import java.util.Optional;

public class SynchronizedClanService implements ClanService {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedClanService.class);

    private final EntityRepository<Clan> repository;
    private final CacheUtility<Clan> cacheUtility;

    public SynchronizedClanService(EntityRepository<Clan> repository) {
        this.repository = repository;
        cacheUtility = new CacheUtility<>(repository, Clan.class);
    }

    @Override
    public Optional<Clan> getClan(long clanId) {
        return cacheUtility.getEntity(clanId);
    }

    @Override
    public boolean changeClanGold(long clanId, int reward) {
        Optional<Clan> clanOptional = getClan(clanId);
        if (clanOptional.isPresent()) {
            Clan clan = clanOptional.get();
            synchronized (clan) {
                int currentGold = clan.getGold();
                int newGoldAmount = currentGold + reward;
                Clan tmpClan = new Clan(clanId, clan.getName(), newGoldAmount);
                try {
                    repository.save(tmpClan);
                    clan.setGold(newGoldAmount);
                    logger.info("Gold changed to Clan ID: {}, Reward: {}, Total Gold: {}", clanId, reward, newGoldAmount);
                    return true;
                } catch (Exception saveException) {
                    logger.error("Failed to save clan after adding gold. Clan ID: {}, Reward: {}", clanId, reward, saveException);
                    return false;
                }
            }
        } else {
            logger.error("Error changing gold to clan. Clan ID: {}, Reward: {}", clanId, reward);
            return false;
        }
    }

}
